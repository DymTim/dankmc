package me.dymtim.dankmc.commands;

import me.dymtim.dankmc.ChatFormat;
import me.dymtim.dankmc.RankFormat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RankCommand extends Command {
    public RankCommand() {
        super("rank", "View a player's current rank.", "/rank", new ArrayList<>());
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                try {
                    sender.sendMessage(ChatFormat.DANKMC.format("&i" + player.getName() + " &nhas the rank&i " + RankFormat.getRank(player) + "&n."));
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatFormat.DANKMC.format("&eInvalid player."));
                }
            } else {
                sender.sendMessage(ChatFormat.DANKMC.format("&eInvalid player."));
            }
        }

        return false;
    }
}
