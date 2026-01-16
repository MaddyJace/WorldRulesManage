package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import static com.maddyjace.worldrulesmanage.util.Ref.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;

/** 方块交互 监听器 */
public class InteractBlock implements Listener {

    // 当玩家与方块交互时
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        // 当玩家是右键点击时处理
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {

            Player player = e.getPlayer();                           // 玩家对象
            World world = player.getWorld();                         // 世界对象
            Location current = e.getClickedBlock().getLocation();    // 坐标位置
            String blockName = e.getClickedBlock().getType().name(); // 方块名称
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
                String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
                // 当没有权限时
                if (!(player.hasPermission(permission))) {
                    Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).interactBlock;
                    if (WorldUtil.conJud(map, enable, checkMode, blockList, blockName)) {
                        e.setCancelled(true);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                        return;
                    }
                }
            }

            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName())) {
                String permission = Ref.wdl.getLocalData().get(world.getName()).permission;
                if (!(player.hasPermission(permission))) {
                    Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).interactBlock;
                    if (WorldUtil.inRadius(world, current)) {
                        if (WorldUtil.conJud(map, enable, checkMode, blockList, blockName)) {
                            e.setCancelled(true);
                            String message = (String) map.get(Ref.message);
                            Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                        }
                    }
                }
            }
        }

    }

}
