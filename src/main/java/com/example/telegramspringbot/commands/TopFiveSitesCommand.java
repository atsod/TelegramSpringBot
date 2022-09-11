package com.example.telegramspringbot.commands;

import com.example.telegramspringbot.service.TelegramBot;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TopFiveSitesCommand {
    @SneakyThrows
    public static void command(TelegramBot bot, Message message) {
        AtomicInteger sitePlace = new AtomicInteger(1);
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        Map<String, String> sites = ImmutableMap.of(
                "Google", "www.google.com",
                "YouTube", "www.youtube.com",
                "PornHub", "www.pornhub.com",
                "Yandex", "www.yandex.ru",
                "Reddit", "www.reddit.com");

        for(Map.Entry<String, String> pair : sites.entrySet()) {
            List<InlineKeyboardButton> button = Collections.singletonList(
                            InlineKeyboardButton.builder()
                                    .text("#" + sitePlace.getAndIncrement() + " " + pair.getKey())
                                    .url(pair.getValue())
                                    .build()
            );
            buttons.add(button);
        }
        buttons.add(Collections.singletonList(
                InlineKeyboardButton.builder()
                        .text("◀️")
                        .callbackData("Back to Main menu")
                        .build()
        ));

        bot.execute(SendMessage.builder()
                .chatId(message.getChatId())
                .text("TOP 5 SITES IN THE WORLD:")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttons)
                        .build())
                .build());
    }
}
