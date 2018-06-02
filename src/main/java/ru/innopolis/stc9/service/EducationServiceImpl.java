package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.EducationDao;
import ru.innopolis.stc9.pojo.Education;

import java.util.List;

/**
 * Created by Сергей on 01.06.2018.
 */
@Service
public class EducationServiceImpl implements EducationService {
    @Autowired
    private EducationDao educationDao;

    @Override
    public List<Education> findAllEducations() {
        return educationDao.findAllEducations();
    }
}
