package ru.innopolis.stc9.service;

import org.junit.Before;
import org.junit.Test;
import ru.innopolis.stc9.dao.SubjectDao;
import ru.innopolis.stc9.dao.SubjectDaoImpl;
import ru.innopolis.stc9.pojo.Education;
import ru.innopolis.stc9.pojo.Subject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubjectServiceImplTest {
    private SubjectServiceImpl subjectService;


    @Before
    public void setUp() {
        Subject subject = new Subject("some");
        Education education = new Education(1, 1);
        List<Subject> listSubject = new ArrayList<>();
        listSubject.add(subject);
        List<Education> listEducation = new ArrayList<>();
        listEducation.add(education);

        SubjectDao mockSubjectDao = mock(SubjectDaoImpl.class);
        EducationService mockEducationService = mock(EducationServiceImpl.class);
        when(mockSubjectDao.addSubject(subject)).thenReturn(false);
        when(mockSubjectDao.deleteSubject(1)).thenReturn(true);
        when(mockSubjectDao.findAllSubject()).thenReturn(listSubject);
        when(mockSubjectDao.findById(1)).thenReturn(subject);
        when(mockEducationService.findAllEducations()).thenReturn(listEducation);

        subjectService = new SubjectServiceImpl(mockSubjectDao, mockEducationService);
    }

    @Test
    public void addSubjectTestFalseEmptyStr() {
        boolean result = subjectService.addSubject("");
        assertFalse(result);
    }

    @Test
    public void addSubjectTest() {
        boolean result = subjectService.addSubject("some");
        assertFalse(result);
    }

    @Test
    public void deleteSubjectTestIfFalse() {
        boolean result = subjectService.deleteSubject(-1);
        assertFalse(result);
    }

    @Test
    public void deleteSubjectTestIfTrue() {
        boolean result = subjectService.deleteSubject(1);
        assertTrue(result);
    }

    @Test
    public void findAllSubjectTest() {
        List<Subject> result = subjectService.findAllSubject();
        assertEquals(result.get(0).getName(), "some");
    }

    @Test
    public void findByIdTest() {
        Subject result = subjectService.findById(1);
        assertEquals(result.getName(), "some");
    }

    @Test
    public void findByGroupIdTest() {
        List<Subject> result = subjectService.findByGroupId(1);
        assertEquals(result.get(0).getName(), "some");
    }

    @Test
    public void checkSubjectNameTest() {
        boolean result = subjectService.checkSubjectName("some");
        assertTrue(result);
    }
}
