package com.soramiyazaki.telegrambotserver.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton KILL_BUTTON = new InlineKeyboardButton("Kill");
    private static final InlineKeyboardButton TNT_BUTTON = new InlineKeyboardButton("TNT");
    private static final InlineKeyboardButton GIANT_BUTTON = new InlineKeyboardButton("Giant Zombie");

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        KILL_BUTTON.setCallbackData("/kill");
        TNT_BUTTON.setCallbackData("/tnt");
        GIANT_BUTTON.setCallbackData("/giant");

        List<InlineKeyboardButton> rowInline = Arrays.asList(START_BUTTON, KILL_BUTTON, TNT_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = Arrays.asList(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;

    }

}
