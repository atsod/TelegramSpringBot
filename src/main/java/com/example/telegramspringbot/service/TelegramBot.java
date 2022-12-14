package com.example.telegramspringbot.service;

import com.example.telegramspringbot.commands.*;
import com.example.telegramspringbot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        //reverse passing stages
        if(update.hasCallbackQuery()) {
            String callDataString = update.getCallbackQuery().getData();
            Message callBackMQueryMessage = update.getCallbackQuery().getMessage();
            if ("Back to Main menu".equals(callDataString)) {
                StartCommand.command(this, callBackMQueryMessage);
            }
        }
        //passing next stage from commands
        else if(message != null && message.hasEntities() && message.hasText()) {
            switch(message.getText()) {
                case "/start":
                    StartCommand.command(this, message);
                    break;
                case "/show_top_five_sites":
                    TopFiveSitesCommand.command(this, message);
                    break;
                case "/show_top_five_games":
                    try {
                        TopFiveGamesCommand.command(this, message);
                    } catch (IOException | TelegramApiException e) {
                        log.error("error occured: " + e.getMessage());
                    }
                    break;
                case "/show_funny_pictures":
                    try {
                        FunnyPicturesCommand.command(this, message);
                    } catch (TelegramApiException e) {
                        log.error("error occured: " + e.getMessage());
                    }
                    break;
                case "/about":
                    AboutCommand.command(this, message);
                    break;
            }
        }
        //passing next stage from Main menu buttons
        else if (message!= null && message.hasText()) {
            switch (message.getText()) {
                case "?????? 5 ???????????? \uD83D\uDC8E":
                    TopFiveSitesCommand.command(this, message);
                    break;
                case "?????? 5 ?????? \uD83D\uDD2B":
                    try {
                        TopFiveGamesCommand.command(this, message);
                    } catch (IOException | TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "?????????????? ???????????????? \uD83D\uDE02":
                    try {
                        FunnyPicturesCommand.command(this, message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "?????????????????????????? ??????":
                    AboutCommand.command(this, message);
                    break;
                case "??????????????????":
                    StartCommand.command(this, message);
                    break;
                default:
                    messageHandler(message);
            }
        }
    }

    private void messageHandler(Message message) {
        //Reversing text messages, if not commands
        char[] letter = message.getText().toCharArray();
        StringBuilder result = new StringBuilder();
        int length = message.getText().length();
        for(int i = 0; i < length; i++) result.append(letter[length - i - 1]);
        try {
            execute(SendMessage.builder()
                    .chatId(message.getChatId())
                    .text("Reverse message:\n" + result)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
