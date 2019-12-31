package me.dymtim.dankmc;

import me.dymtim.dankmc.commands.DiscordCommand;
import me.dymtim.dankmc.commands.RankCommand;
import me.dymtim.dankmc.commands.SetRankCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        enableCommand(new DiscordCommand());
        enableCommand(new RankCommand());
        SetRankCommand setRankCommand = new SetRankCommand();
        enableCommand(setRankCommand);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(setRankCommand, this);
        instance = this;
        saveConfig();
    }

    public void onDisable() {
        saveConfig();
        disableCommands();
    }

    static Main instance;
    public static Main getInstance() {
        return instance;
    }



    private CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);
                commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            }
        } catch (NoSuchFieldException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
            getLogger().log(Level.SEVERE, "Exception while registering commands", e);
        }

        return commandMap;
    }

    private void enableCommand(Command command) {
        CommandMap map = getCommandMap();
        map.register(this.getName(), command);
    }

    private void disableCommands() {
        CommandMap map = getCommandMap();
        map.clearCommands();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        RankFormat.loadRank(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        RankFormat.saveRank(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        e.setFormat(RankFormat.getRank(e.getPlayer()).prefix + "%s §7» §r%s");
    }
}
