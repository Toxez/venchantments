package ua.tox8729.venchantments.utils;

import ua.tox8729.venchantments.VEnchantments;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigUtil {
    private static final VEnchantments plugin = VEnchantments.getInstance();

    public static ConfigurationSection getEnchantmentConfig(String enchantment) {
        return plugin.getConfig().getConfigurationSection("enchantments." + enchantment);
    }

    public static Set<String> getAllowedItems() {
        return Stream.of("drill", "auto-furnace", "magnet")
                .map(ConfigUtil::getEnchantmentConfig)
                .flatMap(section -> section.getStringList("allow-enchantment-items").stream())
                .collect(Collectors.toSet());
    }
}