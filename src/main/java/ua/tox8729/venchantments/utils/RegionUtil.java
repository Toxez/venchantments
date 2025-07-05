package ua.tox8729.venchantments.utils;

import ua.tox8729.venchantments.VEnchantments;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionUtil {
    public static boolean hasBreakPermission(Location location, Player player) {
        if (!VEnchantments.regionProtectionEnabled) {
            return true;
        }
        com.sk89q.worldguard.LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        return query.testBuild(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(location), localPlayer, Flags.BLOCK_BREAK);
    }
}