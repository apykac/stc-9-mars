package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.innopolis.stc9.dao.implementation.HomeWorkDaoImpl;
import ru.innopolis.stc9.dao.interfaces.HomeWorkDao;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.service.implementation.HomeWorkServiceImpl;
import ru.innopolis.stc9.service.interfaces.HomeWorkService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Сергей on 31.05.2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HomeWorkServiceImpl.class})
public class HomeWorkServiceImplTest {
    private HomeWork homeWork;
    private HomeWorkDao homeWorkDao;
    private HomeWorkService homeWorkService;
    private List<HomeWork> list;

    @Before
    public void setUp() throws Exception {
        homeWork = new HomeWork(1,"test", 1, 1);
        homeWorkService = new HomeWorkServiceImpl();
        homeWorkDao = PowerMockito.mock(HomeWorkDaoImpl.class);
        Field field = PowerMockito.field(HomeWorkServiceImpl.class, "homeWorkDao");
        field.set(homeWorkService, homeWorkDao);
        list = new ArrayList<>();
    }

    @Test
    public void addHomeWork() throws Exception {
        PowerMockito.when(homeWorkDao.addHomeWork(homeWork)).thenReturn(true);
        assertTrue(homeWorkService.addHomeWork(homeWork));
    }

    @Test
    public void updateHomeWork() throws Exception {
        PowerMockito.when(homeWorkService.updateHomeWork(homeWork)).thenReturn(true);
        assertTrue(homeWorkService.updateHomeWork(homeWork));
    }

    @Test
    public void findById() throws Exception {
        PowerMockito.when(homeWorkDao.findById(1)).thenReturn(homeWork);
        assertEquals(homeWorkService.findById(1), homeWork);
    }

    @Test
    public void findByStudentId() throws Exception {
        PowerMockito.when(homeWorkDao.findByStudentId(1)).thenReturn(homeWork);
        assertEquals(homeWorkService.findByStudentId(1), homeWork);
    }

    @Test
    public void findByLessonId() throws Exception {
        PowerMockito.when(homeWorkDao.findByLessonId(1)).thenReturn(homeWork);
        assertEquals(homeWorkService.findByLessonId(1), homeWork);
    }

    @Test
    public void findAllHomeWork() throws Exception {
        PowerMockito.when(homeWorkDao.findAllHomeWork()).thenReturn(list);
        assertEquals(homeWorkService.findAllHomeWork(), list);
    }

    @Test
    public void deleteHomeWork() throws Exception {
        PowerMockito.when(homeWorkDao.deleteHomeWork(1)).thenReturn(true);
        assertTrue(homeWorkService.deleteHomeWork(1));
    }

}