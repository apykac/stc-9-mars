package ru.innopolis.stc9.service;

public class Main {
    public static void main(String[] args) {
        AttendanceService attendanceService = new AttendanceServiceImpl();

        int[] students = new int[]{13, 14};

        attendanceService.addLessonAttendance(2, students);
    }

}
