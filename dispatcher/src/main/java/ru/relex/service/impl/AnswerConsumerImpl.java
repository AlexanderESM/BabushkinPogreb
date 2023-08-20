package ru.relex.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.relex.controller.UpdateController;
//import ru.relex.controller.UpdateProcessor;
import ru.relex.service.AnswerConsumer;
import static ru.relex.model.RabbitQueue.ANSWER_MESSAGE;

/**
 * Принимает ответы от RabbitMQ (отправленные из Node) и передаёт в UpdateController
 */

@Service
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }


    //    private final UpdateProcessor updateProcessor;
//
//    public AnswerConsumerImpl(UpdateProcessor updateProcessor) {
//        this.updateProcessor = updateProcessor;
//    }
//
    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
//        updateProcessor.setView(sendMessage);
        updateController.setView(sendMessage);
    }

}
