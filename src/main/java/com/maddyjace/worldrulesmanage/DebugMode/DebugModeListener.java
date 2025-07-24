package com.maddyjace.worldrulesmanage.DebugMode;

import com.maddyjace.worldrulesmanage.ConfigFile.ConfigFile;
import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DebugModeListener implements Listener {

    private final MessageFile messageFile = MessageFile.INSTANCE;

    // 该类阻止玩家与实体交互
    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        String entityName = entity.getType().toString().toLowerCase();

        // 调试模式: 在控制台返回被交互的实体名称
        if(ConfigFile.getConfig("getEntityName") && MessageFile.getMessage("getEntityNameMessage") != null && player.isOp()) {
            messageFile.cmdChatMessage(MessageFile.getMessage("getEntityNameMessage") + entityName);
        }

    }

    // 当玩家使用物品时处理
    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event) {
        Player player = event.getPlayer(); // 获取玩家类型
        ItemStack item = event.getItem();  // 获取物品堆叠
        if(item != null) { // 该对象不能是空的
            String itemName = String.valueOf(item.getType()); // 获取物品常量名称
            // 调试模式: 在控制台输出物品名称
            if(ConfigFile.getConfig("getItemName") && MessageFile.getMessage("getItemNameMessage") != null && player.isOp()) {
                messageFile.cmdChatMessage(MessageFile.getMessage("getItemNameMessage") + itemName);
            }
        }
    }

    // 当玩家与方块交互时
    @EventHandler
    public void onPlayerUseItem01(PlayerInteractEvent event) {
        // 当玩家是右键点击时处理
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer(); // 获取玩家类型
            // 右键点击的方块不能为空(如: 空气)
            if (event.getClickedBlock() != null) {
                String blockName = event.getClickedBlock().getType().toString(); // 获取玩家右键点击的方块常量名称

                // 调试模式: 在控制台输出右键交互的方块名称
                if(ConfigFile.getConfig("getBlockName") && MessageFile.getMessage("getBlockNameMessage") != null && player.isOp()) {
                    messageFile.cmdChatMessage(MessageFile.getMessage("getBlockNameMessage") + blockName);
                }
            }
        }
    }

    // 该方法当玩家触发方块时处理
    @EventHandler
    public void onPlayerUseItem02(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            Player player = event.getPlayer(); // 获取玩家类型
            String blockName = event.getClickedBlock() != null ? event.getClickedBlock().getType().toString() : null;
            if(blockName != null) {
                // 调试模式: 在控制台输出被触发的方块名称
                if(ConfigFile.getConfig("getBlockName") && MessageFile.getMessage("getBlockNameMessage") != null && player.isOp()) {
                    messageFile.cmdChatMessage(MessageFile.getMessage("getBlockNameMessage") + blockName);
                }
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // 获取生物的实体类型名称
        EntityType entityType = event.getEntityType();
        String entityName = entityType.name();

        // 调试模式: 向控制台返回被生成的实体名称
        if(ConfigFile.getConfig("getCreatureSpawnName") && MessageFile.getMessage("getCreatureSpawnNameMessage") != null) {
            messageFile.cmdChatMessage(MessageFile.getMessage("getCreatureSpawnNameMessage") + entityName);
        }
    }

}
