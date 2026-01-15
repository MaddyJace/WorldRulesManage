package com.maddyjace.worldrulesmanage.debug;

import com.maddyjace.worldrulesmanage.util.Config;
import com.maddyjace.worldrulesmanage.util.Get;
import com.maddyjace.worldrulesmanage.util.Language;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Debug implements Listener {
    private static final Config c = Config.INSTANCE;

    // 实体爆炸
    @EventHandler
    public void entityExplode(EntityExplodeEvent e) {
        if (!c.entityExplodeName) return;

        String entityName = e.getEntity().getType().name(); // 实体名称
        String str = Language.Get.translate(Language.getServerLanguage(), "entityExplodeNameDebug", entityName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(entityName);
        }

    }

    // 方块爆炸
    @EventHandler
    public void blockExplode(BlockExplodeEvent e) {
        if (!c.blockExplodeName) return;

        String blockName = e.getBlock().getType().name(); // 实体名称
        String str = Language.Get.translate(Language.getServerLanguage(), "blockExplodeNameDebug", blockName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(blockName);
        }
    }

    // 实体改变方块
    @EventHandler
    public void event(EntityChangeBlockEvent e) {
        if (!c.entityChangeName) return;

        String entityName = e.getBlock().getType().name(); // 方块名称
        String str = Language.Get.translate(Language.getServerLanguage(), "entityChangeNameDebug", entityName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(entityName);
        }
    }

    // 方块燃烧
    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        if (!c.blockBurnName) return;

        String blockName = e.getBlock().getType().name(); // 方块名称
        String str = Language.Get.translate(Language.getServerLanguage(), "blockBurnNameDebug", blockName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(blockName);
        }
    }

    // 实体生成
    @EventHandler
    public void event(CreatureSpawnEvent e) {
        if (!c.creatureSpawnName) return;

        String entityName = e.getEntityType().name(); // 实体名称
        String str = Language.Get.translate(Language.getServerLanguage(), "creatureSpawnNameDebug", entityName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(entityName);
        }
    }

    // 方块破坏
    @EventHandler // (ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!c.blockBreakName) return;

        String blockName = e.getBlock().getType().name(); // 方块名称
        String str = Language.Get.translate(Language.getServerLanguage(), "blockBreakNameDebug", blockName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(blockName);
        }
    }

    // 方块放置
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!c.blockPlaceName) return;

        String blockName = e.getBlock().getType().name(); // 方块名称
        String str = Language.Get.translate(Language.getServerLanguage(), "blockPlaceNameDebug", blockName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(blockName);
        }
    }

    // 物品拿起
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!c.entityPickupItemName) return;

        // 判断是否为玩家
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        String itemName = e.getItem().getItemStack().getType().name(); // 物品名称
        String str = Language.Get.translate(Language.getServerLanguage(), "entityPickupItemNameDebug", itemName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(itemName);
        }
    }

    // 物品丢弃
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!c.playerDropItemName) return;

        String itemName = e.getItemDrop().getItemStack().getType().name(); // 物品名称
        String str = Language.Get.translate(Language.getServerLanguage(), "playerDropItemNameDebug", itemName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(itemName);
        }
    }

    // 方块交互
    @EventHandler
    public void onPlayerRightClickInteract(PlayerInteractEvent e) {
        if (!c.playerInteractRightClickName) return;

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            String blockName = e.getClickedBlock().getType().name(); // 方块名称
            String str = Language.Get.translate(Language.getServerLanguage(), "playerInteractRightClickNameDebug", blockName);
            if (str != null) {
                Get.plugin().getLogger().info(str.replace("&", "§"));
            } else {
                Get.plugin().getLogger().info(blockName);
            }

        }
    }

    // 方块触发
    @EventHandler
    public void onPlayerPhysicalInteract(PlayerInteractEvent e) {
        if (!c.playerPhysicalInteractName) return;

        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock() != null) {
            String blockName = e.getClickedBlock().getType().name(); // 方块名称
            String str = Language.Get.translate(Language.getServerLanguage(), "playerPhysicalInteractNameDebug", blockName);
            if (str != null) {
                Get.plugin().getLogger().info(str.replace("&", "§"));
            } else {
                Get.plugin().getLogger().info(blockName);
            }
        }
    }

    // 方块点燃
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        if (!c.blockIgniteName) return;

        String blockName = e.getBlock().getType().name(); // 方块名称
        String str = Language.Get.translate(Language.getServerLanguage(), "blockIgniteNameDebug", blockName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(blockName);
        }
    }

    // 物品使用
    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent e) {
        if (!c.playerUseItemName) return;

        if (e.getItem() == null) return;
        String itemName = e.getItem().getType().name(); // 方块名称
        String str = Language.Get.translate(Language.getServerLanguage(), "playerUseItemNameDebug", itemName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(itemName);
        }
    }

    // 实体交互
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (!c.playerInteractEntityName) return;

        Entity entity = e.getRightClicked();            // 实体对象
        String entityName = entity.getType().name();    // 实体名称
        String str = Language.Get.translate(Language.getServerLanguage(), "playerInteractEntityNameDebug", entityName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(entityName);
        }
    }
    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (!c.playerInteractEntityName) return;

        Entity entity = e.getRightClicked();            // 实体对象
        if (entity == null) return;
        String entityName = entity.getType().name();    // 实体名称
        String str = Language.Get.translate(Language.getServerLanguage(), "playerInteractEntityNameDebug", entityName);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(entityName);
        }
    }

    // 受伤类型
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (!c.entityDamageName) return;

        if(!(e.getEntity() instanceof Player)) { return; }

        String causeType = e.getCause().name();
        String str = Language.Get.translate(Language.getServerLanguage(), "entityDamageNameDebug", causeType);
        if (str != null) {
            Get.plugin().getLogger().info(str.replace("&", "§"));
        } else {
            Get.plugin().getLogger().info(causeType);
        }
    }

}
