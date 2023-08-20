package ru.relex.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * для отправки сообщений/ответов из Node в RabbitMQ
 */
public interface ProducerService {
    void producerAnswer(SendMessage sendMessage);
}
