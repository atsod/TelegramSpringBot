package com.example.telegramspringbot.commands;

import com.example.telegramspringbot.service.TelegramBot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FunnyPicturesCommand {
    @SneakyThrows
    public static void command(TelegramBot bot, Message message) throws TelegramApiException {
        InputFile firstFile = new InputFile(new File("src/main/resources/pictures/img.png"), "img.png");
        InputFile secondFile = new InputFile(new File("src/main/resources/pictures/img_1.png"), "img_1.png");
        InputFile thirdFile = new InputFile(new File("src/main/resources/pictures/img_2.png"), "img_2.png");
        InputFile fourthFile = new InputFile(new File("src/main/resources/pictures/img_3.png"), "img_3.png");
        List<InputFile> photoList = Arrays.asList(firstFile, secondFile, thirdFile, fourthFile);

        for(int i = 0; i < 4; i++) {
            bot.execute(SendPhoto.builder()
                    .chatId(message.getChatId())
                    .photo(photoList.get(i))
                    .build());
        }
    }
}
