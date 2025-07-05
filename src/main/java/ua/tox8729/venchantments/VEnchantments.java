package ua.tox8729.venchantments;

import ua.tox8729.venchantments.enchantments.EnchantmentRegistry;
import ua.tox8729.venchantments.listeners.PlayerActionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VEnchantments extends JavaPlugin {
    private static VEnchantments instance;
    private static EnchantmentRegistry enchantmentRegistry;
    public static boolean regionProtectionEnabled;

    @Override
    public void onEnable() {
        instance = this;
        regionProtectionEnabled = getServer().getPluginManager().getPlugin("WorldGuard") != null;
        enchantmentRegistry = new EnchantmentRegistry();
        enchantmentRegistry.initializeEnchantments();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerActionListener(), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
    }

    public static VEnchantments getInstance() {
        return instance;
    }

    public static EnchantmentRegistry getEnchantmentRegistry() {
        return enchantmentRegistry;
    }
}