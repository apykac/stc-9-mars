package ru.innopolis.stc9.service;

import org.apache.commons.validator.UrlValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.innopolis.stc9.dao.implementation.HomeWorkDaoImpl;
import ru.innopolis.stc9.dao.interfaces.HomeWorkDao;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.implementation.HomeWorkServiceImpl;
import ru.innopolis.stc9.service.interfaces.HomeWorkService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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
    private UrlValidator urlValidator;
    private MarkDao markDao;

    @Before
    public void setUp() throws Exception {
        homeWork = new HomeWork(1, "test", 1, 1);
        homeWorkService = new HomeWorkServiceImpl();
        homeWorkDao = PowerMockito.mock(HomeWorkDaoImpl.class);
        markDao = PowerMockito.mock(MarkDao.class);
        urlValidator = PowerMockito.mock(UrlValidator.class);
        Field fieldHomeWorkDao = PowerMockito.field(HomeWorkServiceImpl.class, "homeWorkDao");
        Field fieldMarkDao = PowerMockito.field(HomeWorkServiceImpl.class, "markDao");
        Field fieldUrlValidator = PowerMockito.field(HomeWorkServiceImpl.class, "urlValidator");
        fieldHomeWorkDao.set(homeWorkService, homeWorkDao);
        fieldMarkDao.set(homeWorkService, markDao);
        fieldUrlValidator.set(homeWorkService, urlValidator);
        list = new ArrayList<>();
    }

    @Test
    public void homeWorkIsURL() {
        PowerMockito.when(urlValidator.isValid(Mockito.anyString())).thenReturn(true);
        Assert.assertTrue(homeWorkService.homeWorkIsURL("ValidUrl"));
    }

    @Test
    public void addHomeWorkSuccess() {
        PowerMockito.when(homeWorkDao.addHomeWork(homeWork)).thenReturn(true);
        PowerMockito.when(markDao.addMark(Mockito.anyObject())).thenReturn(true);
        assertTrue(homeWorkService.addHomeWork(homeWork));
    }

    @Test
    public void addHomeWorkFailed() {
        PowerMockito.when(homeWorkDao.addHomeWork(homeWork)).thenReturn(false);
        PowerMockito.when(markDao.addMark(Mockito.anyObject())).thenReturn(false);
        Assert.assertFalse(homeWorkService.addHomeWork(homeWork));
    }

    @Test
    public void updateHomeWork() {
        PowerMockito.when(homeWorkService.updateHomeWork(homeWork)).thenReturn(true);
        assertTrue(homeWorkService.updateHomeWork(homeWork));
    }

    @Test
    public void findById() {
        PowerMockito.when(homeWorkDao.findById(1)).thenReturn(homeWork);
        assertEquals(homeWorkService.findById(1), homeWork);
    }


    @Test
    public void findAllHomeWork() {
        PowerMockito.when(homeWorkDao.findAllHomeWork()).thenReturn(list);
        assertEquals(homeWorkService.findAllHomeWork(), list);
    }

    @Test
    public void deleteHomeWork() {
        PowerMockito.when(homeWorkDao.deleteHomeWork(1)).thenReturn(true);
        assertTrue(homeWorkService.deleteHomeWork(1));
    }

    @Test
    public void getHomeWorkListByLessonId() {
        List<HomeWork> list = new ArrayList<>(Collections.singletonList(new HomeWork()));
        PowerMockito.when(homeWorkDao.getHomeWorkListByLessonId(1)).thenReturn(list);
        Assert.assertEquals(list, homeWorkService.getHomeWorkListByLessonId(1));
    }

    @Test
    public void findHomeWorkByMarkId() {
        Mark mark = new Mark(1, 40, 1, 1, "some comment");
        PowerMockito.when(markDao.getMarkById(1)).thenReturn(mark);
        PowerMockito.when(homeWorkDao.findHomeWorkByStudentIdAndLessonId(1, 1)).thenReturn(
                new HomeWork(1, "url", 1, 1));
        Assert.assertEquals("url", homeWorkService.findHomeWorkByMarkId(1));
    }

}