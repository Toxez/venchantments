package ua.tox8729.venchantments.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Collections;
import java.util.Set;

public class CustomEnchantment extends Enchantment {
    private final String name;
    private final int maxLevel;
    private final ConfigurationSection config;

    public CustomEnchantment(ConfigurationSection config) {
        super(NamespacedKey.minecraft(config.getName().toLowerCase()));
        this.name = config.getString("name");
        this.maxLevel = config.getInt("maxLevel", 1);
        this.config = config;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @Override
    public Component displayName(int level) {
        return null;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public EnchantmentRarity getRarity() {
        return null;
    }

    @Override
    public float getDamageIncrease(int level, EntityCategory entityCategory) {
        return 1.0f;
    }

    @Override
    public Set<EquipmentSlot> getActiveSlots() {
        return Collections.emptySet();
    }

    public ConfigurationSection getConfig() {
        return config;
    }
}