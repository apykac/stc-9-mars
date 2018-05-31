package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.ProgressDao;
import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {
    @Autowired
    private ProgressDao progressDao;

    @Override
    public List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark, User user) {
        return progressDao.getProgress(greaterOrEqualMark, lessOrEqualMark, user);
    }
}
