package me.dymtim.dankmc.commands;

import com.sun.istack.internal.NotNull;
import me.dymtim.dankmc.ChatFormat;
import me.dymtim.dankmc.RankFormat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SetRankCommand extends Command implements InventoryHolder, Listener {
    private final Inventory inv;
    private Map<Player, Player> targetMap = new HashMap<>();

    public SetRankCommand() {
        super("setrank", "Sets the rank of the specified user.", "/setrank <player>", new ArrayList<>());
        inv = Bukkit.createInventory(this, 9, "Username Goes Here");
        initializeItems();
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 1 && sender instanceof Player) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                Player player = (Player) sender;
                targetMap.put(player, target);
                player.openInventory(inv);
            }

            return true;
        }

        return false;
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.targetMap.remove(e.getPlayer());
    }

    public void initializeItems() {
        RankFormat[] ranks = RankFormat.values();
        int index = inv.getSize() / 2 - ranks.length / 2;

        for (int i = 0; i < ranks.length; i++) {
            RankFormat rank = ranks[i];
            inv.setItem(index + i, createGUIItem(rank.material, rank.prefix, "§7Set player's rank to " + rank.prefix + "§7.", "§8" + rank.name()));
        }
    }

    private ItemStack createGUIItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> metaLore = Arrays.asList(lore);
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inv;
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) {
            return;
        }

        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            e.setCancelled(true);
            return;
        }

        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() != Material.AIR) {
            List<String> lore = clickedItem.getItemMeta().getLore();
            String rankString = lore.get(1).substring(2);
            try {
                RankFormat rank = RankFormat.valueOf(rankString);
                Player target = targetMap.get(player);
                if (target != null) {
                    RankFormat.setRank(target, rank);
                    player.sendMessage(ChatFormat.DANKMC.format("Set &i" + target.getName() + "&n's rank to " + rank.prefix + "&n."));
                } else {
                    player.sendMessage(ChatFormat.DANKMC.format("&eInvalid player."));
                }
            } catch (IllegalArgumentException ex) {
                player.sendMessage("Illegal rank: " + rankString);
            }
        } else {
            player.sendMessage("Invalid item.");
        }
    }
}
