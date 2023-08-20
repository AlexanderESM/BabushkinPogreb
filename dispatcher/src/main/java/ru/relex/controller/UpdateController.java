package ru.relex.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relex.service.UpdateProducer;
import ru.relex.utils.MessageUtils;
import static ru.relex.model.RabbitQueue.*;

/**
 * этот класс распределяет входяшие сообщения от Бота
 * для разных типов сообщений передаются разные входные параметры для сервиса RabbitMQ
 */

@Component
@Log4j
public class UpdateController {
    //Связываем класс UpdateController с классами
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils; //Создаём объект для передачи в телеграмовский метод SendMessage
    private final UpdateProducer updateProducer; // Передаём update  в RabbitMQ

    /**
     * Связываем этот класс UpdateController, с классом TelegramBot
     * см. описание в классе TelegramBot (private UpdateController updateController;)
     * @param telegramBot
     */
    public void registrBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    /**
     * Внедряем зависимость на MessageUtils в UpdateController
     * Создаём объект для передачи в телеграмовский метод SendMessage м класс MessageUtils
     * @param messageUtils
     * @param updateProducer
     */
    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }




    /**
     * Первичная валидация входящих данных
     * @param update
     */
    public void processUpdate(Update update){
        if(update == null){
            log.error("Метод processUpdate получил NULL");
            return; //завершаем если Null
        }

        if(update.getMessage() != null){
            distributeMessageByType(update);
        }else{
            log.error("Ой-Ой Метод processUpdate получил неподдерживаемый тип сообщения "+ update);
        }

    }


    /**
     * Распределяем сообщения по очередям брокера RabbitMQ
     * @param update
     */
    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if(message.hasText()){
            processTextMessageUpdate(update);
        }else if(message.hasDocument()){
            processDocMessageUpdate(update);
        }else if(message.hasPhoto()){
            processPhotoMessageUpdate(update);
        }else{
            setUnsupportedMessageTypeView(update);
        }
    }


    /** В сервисах нельзя вызвать телеграмовский метод
     * в UpdateController буду передаватся ответы от сервисов
     * создали свой proxy метод который пробросит ответы дальше в Telegram Bot
     * @param sendMessage
     */
    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    /**
     * реализуем методы для раззных типов данных по разным очередям в RabbitMQ
     * @param update
     */
    //TODO
    private void processPhotoMessageUpdate(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        setFileReceivedView(update);
    }


    //TODO
    private void processDocMessageUpdate(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
        setFileReceivedView(update);
    }

    //TODO
    private void processTextMessageUpdate(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }



    /**
     * возвращаем пользователю промежуточное сообщение
     * @param update
     */
    private void setFileReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен и обрабатывается");
        setView(sendMessage);
    }

    /**
     * Возвращщаем в телеграмм сообщение если получили неподдерживаемый тип сообщения
     * см класс MessageUtils
     * @param update
     */
    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения");
        setView(sendMessage);
    }

}
