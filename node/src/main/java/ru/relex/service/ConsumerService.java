package ru.relex.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * считывание сообщений из RabbitMQ
 */
public interface ConsumerService {
    /**
     * методы для очередей
     * (свой метод под каждую очередь)
     * @param update
     */
    void consumeTextMessageUpdates(Update update);
    void consumeDocMessageUpdates(Update update);
    void consumePhotoMessageUpdates(Update update);
}
