package com.soramiyazaki.telegrambotserver;

import com.soramiyazaki.telegrambotserver.Bot.Bot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class TelegramBotServer extends JavaPlugin {
    public Logger logger = getLogger();
    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(this));
            getLogger().log(Level.INFO, "Bot has been initialized");
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> listAllPlayer() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        List<String> names = players.stream().map(player -> player.getName()).collect(Collectors.toList());
        return names;
    }

    public boolean killPlayer(String name) {
        Player player = Bukkit.getPlayer(name);
        if(player != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setHealth(0.0);
                    Bukkit.broadcastMessage("The player was killed!");
                }
            }.runTaskLater(this, 100);
            return true;
        } else {
            return false;
        }
    }

    public boolean spawnTnt(String name) {
        Player player = Bukkit.getPlayer(name);
        if(player == null) return false;
        Bukkit.broadcastMessage("TNT run is starting now!");
        new BukkitRunnable() {
            int tntCount = 15;
            @Override
            public void run() {
                if(tntCount <= 0) {
                    cancel();
                }  else {
                    TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation().add(0, 10, 0), EntityType.PRIMED_TNT);
                    tnt.setFuseTicks(3 * 20);
                    tntCount--;
                }
            }
        }.runTaskTimer(this,  15,15);
        return true;
    }

    public boolean spawnGiant(String name) {
        Player player = Bukkit.getPlayer(name);
        if(player == null) return false;
        Bukkit.broadcastMessage("Something is coming!");
        new BukkitRunnable() {
            @Override
            public void run() {
                player.getWorld().spawnEntity(player.getLocation(), EntityType.GIANT);

            }
        }.runTaskLater(this, 10);
        return true;
    }
    @Override
    public void onDisable() {

    }
}
