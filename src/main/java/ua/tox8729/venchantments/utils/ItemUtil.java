package ua.tox8729.venchantments.utils;

import ua.tox8729.venchantments.VEnchantments;
import ua.tox8729.venchantments.enchantments.CustomEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemUtil {
    public static void addCustomEnchantments(ItemStack itemStack) {
        if (!ConfigUtil.getAllowedItems().contains(itemStack.getType().toString())) return;

        HashMap<org.bukkit.enchantments.Enchantment, Integer> enchantmentMap = new HashMap<>(itemStack.getItemMeta().getEnchants());
        for (CustomEnchantment enchant : VEnchantments.getEnchantmentRegistry().enchantments) {
            if (!enchantmentMap.containsKey(enchant) &&
                    enchant.getConfig().getStringList("allow-enchantment-items").contains(itemStack.getType().toString()) &&
                    !itemStack.getEnchantments().containsKey(enchant)) {
                double chance = new Random().nextDouble() * 100.0;
                if (chance <= enchant.getConfig().getDouble("chance")) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    String loreText = ChatColor.GRAY + enchant.getName();
                    if (itemMeta.hasLore()) {
                        if (!itemMeta.getLore().contains(loreText)) {
                            lore.add(loreText);
                        }
                    } else {
                        lore.add(loreText);
                    }
                    if (itemMeta.hasLore()) {
                        lore.addAll(itemMeta.getLore());
                    }
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    enchantmentMap.put(enchant, 1);
                }
            }
        }
        itemStack.addUnsafeEnchantments(enchantmentMap);
    }
}