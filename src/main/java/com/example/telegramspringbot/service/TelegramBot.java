package com.example.telegramspringbot.service;

import com.example.telegramspringbot.commands.*;
import com.example.telegramspringbot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

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
                        throw new RuntimeException(e);
                    }
                    break;
                case "/show_funny_pictures":
                    break;
                case "/about":
                    try {
                        AboutCommand.command(this, message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
        //passing next stage from Main menu buttons
        else if (message!= null && message.hasText()) {
            switch (message.getText()) {
                case "Топ 5 сайтов \uD83D\uDC8E":
                    TopFiveSitesCommand.command(this, message);
                    return;
                case "Топ 5 игр \uD83D\uDD2B":
                    try {
                        TopFiveGamesCommand.command(this, message);
                    } catch (IOException | TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case "Смешные картинки \uD83D\uDE02":
                    try {
                        FunnyPicturesCommand.command(this, message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case "Дополнительно ☕️":
                    try {
                        AboutCommand.command(this, message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case "Вернуться":
                    StartCommand.command(this, message);
                    return;
            }

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
}
