package com.example.telegramspringbot.commands;

import com.example.telegramspringbot.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

public class AboutCommand {
    public static void command(TelegramBot bot, Message message) throws TelegramApiException {
        String contactInfo = """
                Бот был создан 11.09.2022 \s
                Мой ВК: vk.com/a_tsod\s
                Мой ТГ: @aren_666\s
                \s
                Приятного дня, сладкий :3
                """;
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(
                Collections.singletonList(
                        new KeyboardRow(
                                Collections.singletonList(
                                        new KeyboardButton("Вернуться")))));

        bot.execute(SendMessage.builder()
                .chatId(message.getChatId())
                .text(contactInfo)
                .replyMarkup(keyboardMarkup)
                .build());
    }
}
