package com.example.telegramspringbot.commands;

import com.example.telegramspringbot.service.TelegramBot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StartCommand {
    @SneakyThrows
    public static void command(TelegramBot bot, Message message) {
        String helloWords = """
                Приветствую!\s
                
                 Этот бот выполняет роль небольшого справочника. Тут есть такие команды, как\s
                 /show_top_five_sites - Показывает топ 5 популярных сайтов мира.\s
                 /show_top_five_games - Показывает топ 5 популярных игр мира.\s
                 /show_funny_pictures - Показывает смешные картинки.\s
                 /about - Показывает некоторую контактную информацию. \s
                 
                 А также имеется некоторая функция обработки введеных слов. Так что смело вводи слово и смотри, что получилось!
                 """;
        List<List<ReplyKeyboardMarkup>> menuButtons = new ArrayList<>();
        AtomicInteger callBackDataForButtons = new AtomicInteger(1);
        List<String> buttonNames = Arrays.asList(
                "Топ 5 сайтов \uD83D\uDC8E",
                "Топ 5 игр \uD83D\uDD2B",
                "Смешные картинки \uD83D\uDE02",
                "Дополнительно ☕️");

        KeyboardRow firstRow = new KeyboardRow(Arrays.asList(new KeyboardButton(buttonNames.get(0)),new KeyboardButton(buttonNames.get(1))));
        KeyboardRow secondRow = new KeyboardRow(Arrays.asList(new KeyboardButton(buttonNames.get(2)),new KeyboardButton(buttonNames.get(3))));
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(Arrays.asList(firstRow, secondRow));

        bot.execute(SendMessage.builder()
                .chatId(message.getChatId())
                .text(helloWords)
                .replyMarkup(keyboardMarkup)
                .build());
    }
}
