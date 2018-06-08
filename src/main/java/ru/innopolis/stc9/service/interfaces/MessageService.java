package ru.innopolis.stc9.service.interfaces;

import org.springframework.util.MultiValueMap;

import java.util.List;

public interface MessageService {
    List<String> isCorrectData(MultiValueMap<String, String> incParam);

    boolean addMessage(MultiValueMap<String, String> incParam);
}
