package com.soramiyazaki.telegrambotserver.Bot;

import com.soramiyazaki.telegrambotserver.TelegramBotServer;
import com.soramiyazaki.telegrambotserver.components.BotCommmands;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.logging.Level;


public class Bot extends TelegramLongPollingBot implements BotCommmands {
        private TelegramBotServer plugin;
        public Bot(TelegramBotServer plugin) {
            this.plugin = plugin;
            try {
                this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
            } catch (TelegramApiException ex) {
                ex.getMessage();
            }
        }
    @Override
    public void onUpdateReceived(Update update) {
        String receivedMessage;
        long chat_id;

        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();
            if(update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botCommands(receivedMessage, chat_id);
                plugin.logger.log(Level.INFO, update.getMessage().getText());
            } else if(update.hasCallbackQuery()) {
                plugin.logger.log(Level.INFO, update.getMessage().getText());
                plugin.logger.log(Level.INFO, update.getCallbackQuery().getMessage().getText());
                chat_id = update.getCallbackQuery().getMessage().getChatId();
                receivedMessage = update.getCallbackQuery().getData();

                botCommands(receivedMessage, chat_id);
            }
        }
    }

        private void botCommands(String receivedMessage, long chat_id) {
            String[] splittedMessage = receivedMessage.split(" ");
            switch (splittedMessage[0]) {
            case "/start":
                startBot(chat_id);
                break;
            case "/kill":
                if(splittedMessage.length < 2) {
                    sendMessage("You didn't provide a player's name", chat_id);
                } else {
                    killPlayer(chat_id, splittedMessage[1]);
                }
                break;

            case "/tnt":
                if(splittedMessage.length < 2) {
                    sendMessage("You didn't provide a player's name", chat_id);
                } else {
                    spawnTnt(chat_id, splittedMessage[1]);
                }
               break;
            case "/giant":
                if(splittedMessage.length < 2) {
                    sendMessage("You didn't provide a player's name", chat_id);
                } else {
                    spawnGiant(chat_id, splittedMessage[1]);
                }
                break;

        }
    }

    private void startBot(long chat_id) {
        List<String> players = plugin.listAllPlayer();
        if(!players.isEmpty()) {
            for(String player: players) {
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText(player);
                try {
                    execute(message);
                } catch (TelegramApiException exception) {
                    exception.getMessage();
                }
            }
        } else {
            SendMessage message = new SendMessage();
            message.setChatId(chat_id);
            message.setText("Server is empty");
            try {
                execute(message);
            } catch (TelegramApiException exception) {
                exception.getMessage();
            }
        }
    }

    private void spawnGiant(long chat_id, String playerName) {
        boolean isSpawned = plugin.spawnGiant(playerName);
        if(isSpawned) sendMessage("Zombie has been spawned!", chat_id);
        else sendMessage("Something went wrong!", chat_id);
    }

    private void spawnTnt(long chat_id, String playerName) {
        boolean isSpawned = plugin.spawnTnt(playerName);
        if(isSpawned) sendMessage("Success!", chat_id);
        else sendMessage("Something went wrong!", chat_id);
    }
    private void killPlayer(long chat_id, String playerName) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);

        boolean isKilled = plugin.killPlayer(playerName);
        if(isKilled) {
            message.setText(String.format("The player %s was killed", playerName));
        } else message.setText(String.format("The player %s doesn't exist", playerName));

        try {
            execute(message);
        } catch (TelegramApiException exception) {
            exception.getMessage();
        }
    }

    private void sendMessage(String messageText, long chat_id) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException exception) {
            exception.getMessage();
        }
    }
    @Override
    public String getBotUsername() {
        return plugin.getConfig().getString("telegram-bot-name");
    }

    @Override
    public String getBotToken() {
        return plugin.getConfig().getString("telegram-api-key");
    }
}
