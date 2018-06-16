package ru.innopolis.stc9.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import ru.innopolis.stc9.dao.interfaces.EducationDao;
import ru.innopolis.stc9.pojo.Education;
import ru.innopolis.stc9.service.implementation.EducationServiceImpl;
import ru.innopolis.stc9.service.interfaces.EducationService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EducationServiceImplTest {
    private EducationService educationService;
    private EducationDao educationDao;

    @Before
    public void setUp() throws IllegalAccessException {
        educationService = new EducationServiceImpl();
        educationDao = PowerMockito.mock(EducationDao.class);
        Field field = PowerMockito.field(EducationServiceImpl.class, "educationDao");
        field.set(educationService, educationDao);
    }

    @Test
    public void findAllEducationsTest() {
        List<Education> list = new ArrayList<>(Collections.singletonList(new Education()));
        PowerMockito.when(educationDao.findAllEducations()).thenReturn(list);
        Assert.assertEquals(list,educationService.findAllEducations());
    }
}
