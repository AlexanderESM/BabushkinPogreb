package ru.relex.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.service.UpdateProducer;

/**
 * Передаём update  в RabbitMQ
 */

@Service
@Log4j
public class UpdateProducerImpl implements UpdateProducer {
    /**
     * Передаём сообщеня из UpdateProducerImpl в RabbitMQ
     */
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void produce(String rabbitQueue, Update update) {
        log.debug(update.getMessage().getText());
        /**
         * Spring создаёт Bean.
         * Передаём название очереди и update который будет преобразован в json
         */
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}
