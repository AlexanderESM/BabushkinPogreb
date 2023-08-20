package ru.relex.service;

import org.telegram.telegrambots.meta.api.objects.Update;
//обработка входящих сообщений в зависимости от типа входящих сообщений
public interface MainService {
    void processTextMessage(Update update);
    void processDocMessage(Update update);
    void processPhotoMessage(Update update);
}
