package ua.tox8729.venchantments.enchantments;

import ua.tox8729.venchantments.VEnchantments;
import ua.tox8729.venchantments.utils.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.*;

public class EnchantmentHandler {
    private static final Set<Material> AIR_TYPES = Set.of(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);

    public static void handleBlockBreak(Player player, Block block, ItemStack tool, boolean cancelDrops) {
        if (tool.getType() == Material.AIR || !tool.hasItemMeta()) return;
        if (!RegionUtil.hasBreakPermission(block.getLocation(), player)) return;

        Map<Enchantment, Integer> enchantments = tool.getItemMeta().getEnchants();
        EnchantmentRegistry registry = VEnchantments.getEnchantmentRegistry();
        boolean hasDrill = enchantments.containsKey(registry.DRILL);
        boolean hasMagnet = enchantments.containsKey(registry.MAGNET);
        boolean hasAutoFurnace = enchantments.containsKey(registry.AUTO_FURNACE);

        Map<Block, Collection<ItemStack>> drops = new HashMap<>();
        List<Block> brokenBlocks = new ArrayList<>();
        brokenBlocks.add(block);
        drops.put(block, block.getDrops(tool));

        if (hasDrill) {
            block.getDrops(tool).clear();
            Location center = block.getLocation();
            int radius = registry.DRILL.getConfig().getInt("size");

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block target = center.getWorld().getBlockAt(center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
                        if (!AIR_TYPES.contains(target.getType()) &&
                                (target.getType() != Material.BEDROCK || player.getGameMode() == GameMode.CREATIVE) &&
                                RegionUtil.hasBreakPermission(target.getLocation(), player)) {
                            brokenBlocks.add(target);
                            drops.put(target, target.getDrops(tool));
                            target.setType(Material.AIR);
                        }
                    }
                }
            }
        }

        if (hasAutoFurnace) {
            Iterator<Recipe> recipes = Bukkit.recipeIterator();
            while (recipes.hasNext()) {
                Recipe recipe = recipes.next();
                if (!(recipe instanceof FurnaceRecipe)) continue;
                FurnaceRecipe furnaceRecipe = (FurnaceRecipe) recipe;

                for (Map.Entry<Block, Collection<ItemStack>> entry : drops.entrySet()) {
                    Collection<ItemStack> drop = entry.getValue();
                    Collection<ItemStack> newDrop = new ArrayList<>();
                    for (ItemStack item : drop) {
                        if (furnaceRecipe.getInputChoice().test(item)) {
                            ItemStack result = furnaceRecipe.getResult();
                            result.setAmount(item.getAmount());
                            newDrop.add(result);
                        } else {
                            newDrop.add(item);
                        }
                    }
                    drops.replace(entry.getKey(), newDrop);
                }
            }
        }

        if (hasMagnet) {
            if (drops.isEmpty()) return;
            for (Map.Entry<Block, Collection<ItemStack>> entry : drops.entrySet()) {
                for (ItemStack item : entry.getValue()) {
                    player.getInventory().addItem(item);
                }
            }
        } else {
            for (Map.Entry<Block, Collection<ItemStack>> entry : drops.entrySet()) {
                Block target = entry.getKey();
                for (ItemStack item : entry.getValue()) {
                    target.getWorld().dropItemNaturally(target.getLocation().add(0.5, 0.0, 0.5), item);
                }
            }
        }

        if (!enchantments.containsKey(Enchantment.MENDING)) {
            tool.setDurability((short) (tool.getDurability() + brokenBlocks.size()));
        }

        player.updateInventory();
    }
}