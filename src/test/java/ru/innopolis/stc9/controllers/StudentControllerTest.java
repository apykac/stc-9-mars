package ru.innopolis.stc9.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.innopolis.stc9.TestContext;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.interfaces.LessonsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class StudentControllerTest {
    @Autowired
    LessonsService lessonsService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        StudentController studentController = new StudentController(lessonsService);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void viewLessonForSubjectTest() throws Exception {
        List<Lessons> list = new ArrayList<>(Arrays.asList(new Lessons(), new Lessons()));
        list.get(0).setId(1);
        list.get(1).setId(2);
        PowerMockito.when(lessonsService.findAllLessonsByWithSubject(1)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/university/student/subject/{subjectId}", 1)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("views/studentLessons")).
                andExpect(MockMvcResultMatchers.model().attribute("lessons", list));
    }
}
