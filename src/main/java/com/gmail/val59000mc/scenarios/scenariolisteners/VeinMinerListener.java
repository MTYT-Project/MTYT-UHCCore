package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.configuration.LootConfiguration;
import com.gmail.val59000mc.configuration.MainConfig;
import com.gmail.val59000mc.customitems.UhcItems;
import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.scenarios.Option;
import com.gmail.val59000mc.scenarios.Scenario;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import com.gmail.val59000mc.utils.UniversalMaterial;
import com.gmail.val59000mc.utils.UniversalSound;
import com.gmail.val59000mc.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class VeinMinerListener extends ScenarioListener {

    private static final BlockFace[] BLOCK_FACES = new BlockFace[]{
            BlockFace.DOWN,
            BlockFace.UP,
            BlockFace.SOUTH,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.WEST
    };

    @Option(key = "calculate-tool-damage")
    private boolean calculateToolDamage = true;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Map<Material, LootConfiguration<Material>> blockLoots = GameManager.getGameManager().getConfig().get(MainConfig.ENABLE_BLOCK_LOOT) ? GameManager.getGameManager().getConfig().get(MainConfig.BLOCK_LOOT) : new HashMap<>();
        /*blockLoots.forEach((material, materialLootConfiguration) -> {
            StringBuilder builder = new StringBuilder();
            materialLootConfiguration.getLoot().forEach(itemStack -> {
                builder.append(itemStack.getAmount()).append("*").append(itemStack.getType().getKey().getKey()).append("+ ");
            });
            Bukkit.getLogger().info(material.getKey().getKey() + ";" + builder);
        });*/
        Player player = e.getPlayer();

        if (player.isSneaking()) {
            return;
        }

        Block block = e.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (block.getType() == UniversalMaterial.GLOWING_REDSTONE_ORE.getType()) {
            block.setType(Material.REDSTONE_ORE);
            Bukkit.getLogger().info("Changed Glowing redstone to normal one");
        }

        if (block.getType() == Material.AIR) {
            return;
        }

        int xp = 0;
        boolean orecheck = false;
        switch (block.getType()) {
            case COAL_ORE:
            case REDSTONE_ORE:
                orecheck = true;
                xp = 1;
                break;
            case COPPER_ORE:
            case LAPIS_LAZULI:
            case GOLD_ORE:
            case NETHER_QUARTZ_ORE:
            case DIAMOND:
                orecheck = true;
                xp = 3;
                break;
            case IRON_ORE:
            case NETHER_GOLD_ORE:
                orecheck = true;
                xp = 2;
                break;
            case EMERALD_ORE:
                orecheck = true;
                xp = 10;
                break;
            case ANCIENT_DEBRIS:
                orecheck = true;
                xp = 4;
                break;
            default:
                break;
        }
        if (!orecheck) {
            Bukkit.getLogger().info("Not an ore...");
            return;
        }
        Vein vein = new Vein(block);
        Material material = e.getBlock().getType();
        vein.process();
        int amount = vein.getOres() * getVeinMultiplier(block.getType());
        // find all surrounding blocks
        int amntlo = 1;
        int amnthi = 3;
        int b = (int) (Math.random() * (amnthi - amntlo + 1) + amntlo);
        Bukkit.getLogger().info(String.valueOf(player.getInventory().firstEmpty()));
        switch (material) {
            case COAL_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.TORCH, amount * 4));
                break;
            case COPPER_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.BLAZE_POWDER, amount * b));
                break;
            case IRON_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.IRON_INGOT, amount * b));
                break;
            case LAPIS_ORE:
                Bukkit.getLogger().info(block.getType().name());
                int min = 0;
                int max = 1;
                int c = (int) (Math.random() * (max - min + 1) + min);
                switch (c) {
                    case 0:
                        e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.LAPIS_LAZULI, amount * b));
                        e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.LAPIS_LAZULI, amount * b));
                        break;
                    case 1:
                        e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.LEATHER, amount * b));
                        break;
                }
                break;
            case GOLD_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.GOLD_INGOT, amount));
                break;
            case NETHER_GOLD_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.GOLD_NUGGET, amount * b * 2));
                break;
            case DIAMOND_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.DIAMOND, amount * b));
                break;
            case REDSTONE_ORE:
                Bukkit.getLogger().info(block.getType().name());
                int minx = 0;
                int maxx = 1;
                int bx = (int) (Math.random() * (maxx - minx + 1) + minx);
                switch (bx) {
                    case 0:
                        e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.NETHER_WART, amount * b));
                        break;
                    case 1:
                        e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.BREWING_STAND, amount * b));
                        break;
                }
                break;
            case EMERALD_ORE:
                Bukkit.getLogger().info(block.getType().name());
                UhcItems.spawnExtraXp(player.getLocation(), xp);
                break;
            case NETHER_QUARTZ_ORE:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.QUARTZ, amount * b));
                break;
            case ANCIENT_DEBRIS:
                Bukkit.getLogger().info(block.getType().name());
                e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.NETHERITE_INGOT, amount));
                break;
        }

        UhcItems.spawnExtraXp(player.getLocation(), xp);

        // Process blood diamonds.
        if (isEnabled(Scenario.BLOOD_DIAMONDS) && block.getType() == Material.DIAMOND_ORE) {
            player.getWorld().playSound(player.getLocation(), UniversalSound.PLAYER_HURT.getSound(), 1, 1);

            if (player.getHealth() < vein.getOres()) {
                VersionUtils.getVersionUtils().killPlayer(player);
            } else {
                player.setHealth(player.getHealth() - vein.getOres());
            }
        }

        if (calculateToolDamage) {
            tool.setDurability((short) (tool.getDurability() + vein.getOres()));
        }
    }

    private int getVeinMultiplier(Material oreType) {
        int multiplier = 1;
        if (getScenarioManager().isEnabled(Scenario.TRIPLE_ORES)) {
            multiplier *= 3;
        }
        if (getScenarioManager().isEnabled(Scenario.DOUBLE_ORES)) {
            multiplier *= 2;
        }
        if ((oreType == Material.GOLD_ORE || oreType == Material.NETHER_GOLD_ORE) && getScenarioManager().isEnabled(Scenario.DOUBLE_GOLD)) {
            multiplier *= 2;
        }
        return multiplier;
    }

    private static class Vein {
        private final Block startBlock;
        private final Material type;
        private int ores;

        public Vein(Block startBlock) {
            this.startBlock = startBlock;
            this.type = startBlock.getType();
            ores = 0;
        }

        public void process() {
            getVeinBlocks(startBlock, type, 2, 200);
        }

        public int getOres() {
            return ores;
        }

        private void getVeinBlocks(Block block, Material type, int i, int maxBlocks) {
            if (maxBlocks == 0) return;

            if (block.getType() == UniversalMaterial.GLOWING_REDSTONE_ORE.getType()) {
                block.setType(Material.REDSTONE_ORE);
            }

            if (block.getType() == type) {
                block.setType(Material.AIR);
                ores++;
                i = 2;
            } else {
                i--;
            }

            // Max ores per vein is 20 to avoid server lag when mining sand / gravel.
            if (i > 0 && ores < 20) {
                for (BlockFace face : BLOCK_FACES) {
                    getVeinBlocks(block.getRelative(face), type, i, maxBlocks - 1);
                }
            }
        }
    }

}