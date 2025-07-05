package ua.tox8729.venchantments.listeners;

import ua.tox8729.venchantments.enchantments.EnchantmentHandler;
import ua.tox8729.venchantments.utils.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class PlayerActionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        event.setDropItems(false);
        EnchantmentHandler.handleBlockBreak(player, event.getBlock(), tool, true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled() || !(event.getWhoClicked() instanceof Player)) return;

        Inventory inv = event.getInventory();
        if (!(inv instanceof AnvilInventory)) return;

        InventoryView view = event.getView();
        int rawSlot = event.getRawSlot();
        if (rawSlot == view.convertSlot(rawSlot) && rawSlot == 2) {
            ItemStack item = event.getCurrentItem();
            if (item != null) {
                ItemUtil.addCustomEnchantments(item);
            }
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        ItemUtil.addCustomEnchantments(event.getItem());
    }
}