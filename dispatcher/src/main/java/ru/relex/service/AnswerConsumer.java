package ru.relex.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

// 30 создали interface

/**
 * Принимает ответы от RabbitMQ и передаёт в UpdateController
 * принимает объект SendMessage
 * имя очереди указывается в сервисе с помощью аннотаци
 */
public interface AnswerConsumer {
    void consume(SendMessage sendMessage);
}
