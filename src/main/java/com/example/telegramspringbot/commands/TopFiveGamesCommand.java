package com.example.telegramspringbot.commands;

import com.example.telegramspringbot.service.TelegramBot;
import com.google.common.collect.ImmutableMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TopFiveGamesCommand {
    public static void command(TelegramBot bot, Message message) throws IOException, TelegramApiException {
        Map<String, String> gameMap = ImmutableMap.of(
                "PUBG: Battlegrounds", "https://global.battlegrounds.pubg.com/",
                "Fortnite", "https://www.epicgames.com/fortnite/ru/home",
                "Grand Theft Auto 5", "https://www.rockstargames.com/gta-v",
                "Minecraft", "https://www.minecraft.net/ru-ru",
                "Super Mario Bros", "https://supermariobros.io/"
        );
        List<List<InlineKeyboardButton>> gameButtons = new ArrayList<>();
        AtomicInteger gamePlace = new AtomicInteger(1);

        for(Map.Entry<String, String> pair : gameMap.entrySet()) {
            gameButtons.add(Collections.singletonList(InlineKeyboardButton.builder()
                    .text("#" + gamePlace.getAndIncrement() + " " + pair.getKey())
                    .url(pair.getValue())
                    .build()));
        }
        gameButtons.add(Collections.singletonList(InlineKeyboardButton.builder()
                .text("◀️")
                .callbackData("Back to Main menu")
                .build()));

        bot.execute(SendMessage.builder()
                .chatId(message.getChatId())
                .text("TOP 5 GAMES IN 2022")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(gameButtons)
                        .build())
                .build());
    }
}
