package me.dymtim.dankmc;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public enum RankFormat {
    DEFAULT("§7", Material.GRASS),
    OWNER("§c§lOWNER§c ", Material.DIAMOND_BLOCK);

    public final String prefix;
    public final Material material;

    RankFormat(String prefix, Material material) {
        this.prefix = prefix;
        this.material = material;
    }

    private static Map<Player, RankFormat> ranks = new HashMap<>();

    public static void setRank(Player player, RankFormat rank) {
        ranks.put(player, rank);
        updateDisplayName(player);
    }

    public static void loadRank(Player player) {
        String rankString = Main.getInstance().getConfig().getString(player.getUniqueId() + ".rank");
        RankFormat rank = (rankString == null) ? RankFormat.DEFAULT : RankFormat.valueOf(rankString);
        ranks.put(player, rank);
        updateDisplayName(player);
    }

    public static void saveRank(Player player) {
        Main.getInstance().getConfig().set(player.getUniqueId() + ".rank", getRank(player).name());
        Main.getInstance().getConfig().set(player.getUniqueId() + ".username", player.getName());
        ranks.remove(player);
    }

    public static void updateDisplayName(Player player) {
        RankFormat rank = getRank(player);
        player.setDisplayName(rank.prefix + player.getName());
        player.setPlayerListName(rank.prefix + player.getName());
    }

    public static RankFormat getRank(Player player) {
        return ranks.get(player);
    }
}
