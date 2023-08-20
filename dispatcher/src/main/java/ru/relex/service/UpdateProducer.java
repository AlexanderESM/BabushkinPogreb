package ru.relex.service;

import org.telegram.telegrambots.meta.api.objects.Update;

//29 создали interface

/**
 * Передаём update  в RabbitMQ
 * принимает название очереди и update в виде данных
 */
public interface UpdateProducer {
    void produce(String rabbitQueue, Update update);
}
