package ru.innopolis.stc9;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.innopolis.stc9.dao.implementation.GroupDaoImpl;
import ru.innopolis.stc9.dao.implementation.UserDaoImpl;
import ru.innopolis.stc9.dao.interfaces.GroupDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.service.implementation.GroupServiceImpl;
import ru.innopolis.stc9.service.implementation.StudentServiceImpl;
import ru.innopolis.stc9.service.implementation.UserServiceImpl;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.StudentService;
import ru.innopolis.stc9.service.interfaces.UserService;

/**
 * Created by Сергей on 11.06.2018.
 */
@Configuration
public class TestContext {



    @Bean
    public GroupService groupService() {
        return Mockito.mock(GroupServiceImpl.class);
    }

    @Bean
    public GroupDao groupDao() {
        return Mockito.mock(GroupDaoImpl.class);
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserServiceImpl.class);
    }

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public StudentService studentService() {
        return new StudentServiceImpl();
    }
}
