package com.example.telegramauth.service;

import com.example.telegramauth.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private BotConfig config;
    public TelegramBot(BotConfig config) {
        this.config = config;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                sendWebAppButton(chatId);
            }
        }
    }

    private void sendWebAppButton(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Нажми на кнопку, чтобы открыть сайт:");


        // Кнопка с WebApp
        InlineKeyboardButton button = new InlineKeyboardButton("Открыть WebApp");
        // Создаем WebAppInfo с URL сайта
        button.setWebApp(new WebAppInfo("https://aa54-118-240-254-100.ngrok-free.app/"));

        // Добавляем кнопку в клавиатуру
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = List.of(button);
        markup.setKeyboard(List.of(row));

        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
