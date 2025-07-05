package ua.tox8729.venchantments.enchantments;

import ua.tox8729.venchantments.VEnchantments;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public class EnchantmentRegistry {
    public final CustomEnchantment DRILL;
    public final CustomEnchantment AUTO_FURNACE;
    public final CustomEnchantment MAGNET;
    public final Set<CustomEnchantment> enchantments;

    public EnchantmentRegistry() {
        DRILL = new CustomEnchantment(
                VEnchantments.getInstance().getConfig().getConfigurationSection("enchantments.drill")
        );
        AUTO_FURNACE = new CustomEnchantment(
                VEnchantments.getInstance().getConfig().getConfigurationSection("enchantments.auto-furnace")
        );
        MAGNET = new CustomEnchantment(
                VEnchantments.getInstance().getConfig().getConfigurationSection("enchantments.magnet")
        );
        enchantments = Set.of(DRILL, AUTO_FURNACE, MAGNET);
    }

    public void initializeEnchantments() {
        for (CustomEnchantment enchantment : enchantments) {
            if (!Arrays.asList(Enchantment.values()).contains(enchantment)) {
                registerEnchantment(enchantment);
            }
        }
    }

    private void registerEnchantment(Enchantment enchantment) {
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}