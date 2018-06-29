package ru.innopolis.stc9.service;

import org.apache.commons.validator.UrlValidator;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.innopolis.stc9.dao.interfaces.HomeWorkDao;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.pojo.HomeWork;
import ru.innopolis.stc9.service.implementation.HomeWorkServiceImpl;
import ru.innopolis.stc9.service.interfaces.HomeWorkService;

import java.util.List;


@RunWith(PowerMockRunner.class)
@PrepareForTest({HomeWorkServiceImpl.class})
public class HomeWorkServiceImplTest {
    private HomeWork homeWork;
    private HomeWorkDao homeWorkDao;
    private HomeWorkService homeWorkService;
    private List<HomeWork> list;
    private UrlValidator urlValidator;
    private MarkDao markDao;

    /*@Before
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
    public void findById() {
        PowerMockito.when(homeWorkDao.findById(1)).thenReturn(homeWork);
        assertEquals(homeWorkService.findById(1), homeWork);
    }
*/

}
