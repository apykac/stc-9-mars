package ru.innopolis.stc9.service;

public class Main {
    public static void main(String[] args) {
        HomeWorkServiceImpl homeWorkService = new HomeWorkServiceImpl();
        System.out.println(homeWorkService.findHomeWorkByMarkId(5));
    }

}
