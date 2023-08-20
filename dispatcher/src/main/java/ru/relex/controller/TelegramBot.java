package ru.relex.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

// 6 создали класс отвечающий за взаимодействие с ботом (с сервером телеграмма)
// Реализация LongPollingBot - бот сам регулярно запрашивает данные от сервера Телеграм
// постоянно используется интернет канал и ресурс системы
//8 добавили анотацию
@Component  //спринг создаст бин
@Log4j //19 B
public class TelegramBot extends TelegramLongPollingBot {
    // 13 BotName and Token теперь в application.propertie
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    /**
     * Связываем c классом UpdateController
     * В класс TelegramBot внедряется ссылка на UpdateController.
     * После внедрения зависимости выполняется метод init(),
     * в этом методе передаём ссылку на TelegramBot внутрь UpdateController
     * это позволяет передавать сообщения из TelegramBot в UpdateController
     * и в обратную сторону ответы от UpdateController в TelegramBot
     */
    private UpdateController updateController;
    public TelegramBot(UpdateController updateController) {this.updateController = updateController;}
    @PostConstruct
    public void init(){updateController.registrBot(this);}






    /* 19 A
    // 17
    private static final Logger log = Logger.getLogger(TelegramBot.class);
    */


    /* 14

   //7 имплементировали методы
    @Override
    public String getBotUsername() {
        return "Ork_BabushkinPogreb_bot"; //9
    }

    @Override
    public String getBotToken() {
        return "6055506783:AAEServjV3QnkmXrXMJZUhBCQT0dS64Ea2Y"; //10
    }
    */

    // 15
    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    // получаем сообщение от пользователя.
    @Override
    public void onUpdateReceived(Update update) {
       /* 35 удалии всё к херам
            //11
            var oroginalMessage = update.getMessage();
            //18
            log.debug(oroginalMessage.getText());
            // 17A  System.out.println(oroginalMessage.getText());
            //21 Отправляем обратно в чат
            var response = new SendMessage();
            response.setChatId(oroginalMessage.getChatId().toString()); // получили чат-id от отпраивтеля
            response.setText("Hello from class TelegramBot"); // добавили текст
            sendAnswerMessage(response); // вызываем метод
        */
        //36
        updateController.processUpdate(update);
    }

    // 20 отправляем обратно в чат
        // SendMessage - это класса Телеграма
        //данные класс будет выступать в качестве view (MVC)
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}


