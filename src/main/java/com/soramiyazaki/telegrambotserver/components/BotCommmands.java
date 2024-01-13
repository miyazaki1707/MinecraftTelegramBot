package com.soramiyazaki.telegrambotserver.components;


import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Arrays;
import java.util.List;

public interface BotCommmands {
    List<BotCommand> LIST_OF_COMMANDS = Arrays.asList(
            new BotCommand("/start", "List of all active players!"),
            new BotCommand("/kill", "Kill a player"),
            new BotCommand("/tnt", "Spawn a tnt"),
            new BotCommand("/giant", "Spawn a Giant Zombie")
    );
}
