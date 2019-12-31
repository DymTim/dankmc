package me.dymtim.dankmc.commands;

import me.dymtim.dankmc.ChatFormat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class DiscordCommand extends Command {
    public DiscordCommand() {
        super("discord", "Invites users to the Discord server.", "/discord", new ArrayList<String>());
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        sender.sendMessage(ChatFormat.DANKMC.format("https://discord.gg/WKCfwQ8"));
        return false;
    }
}
