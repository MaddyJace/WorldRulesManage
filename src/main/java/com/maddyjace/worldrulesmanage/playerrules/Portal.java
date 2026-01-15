package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;
import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 进传送门 事件监听器 */
public class Portal implements Listener {


    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        Player player = e.getPlayer();                 // 玩家对象
        World world = player.getWorld();               // 世界对象
        Block block = player.getLocation().getBlock(); // 方块类型
        Material type = block.getType();               // 材质类型

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).portal;
                if (WorldUtil.conJud(map, enable)) {
                    if ((boolean) map.get(("all"))) {
                        e.setCancelled(true);
                        pushAwayPlayer(player);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                        return;
                    }
                    if ((boolean) map.get(("nether")) && type == Material.PORTAL) {
                        e.setCancelled(true);
                        pushAwayPlayer(player);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                        return;
                    }
                    if ((boolean) map.get(("ender")) && type == Material.ENDER_PORTAL) {
                        e.setCancelled(true);
                        pushAwayPlayer(player);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    }
                }
            }
        }
    }

    // 指定范围触发
    @EventHandler
    public void onPlayerPortalRadius(PlayerPortalEvent e) {

        Player player = e.getPlayer();                        // 玩家对象
        World world = player.getWorld();                      // 世界对象
        Location current = player.getLocation();              // 坐标位置
        Block block = player.getLocation().getBlock(); // 方块类型
        Material type = block.getType();               // 材质类型

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getLocalData().get(world.getName()).permission;
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).portal;
                if (WorldUtil.inRadius(world, current)) {
                    if (WorldUtil.conJud(map, enable)) {
                        if ((boolean) map.get(("all"))) {
                            e.setCancelled(true);
                            pushAwayPlayer(player);
                            String message = (String) map.get(Ref.message);
                            Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                            return;
                        }
                        if ((boolean) map.get(("nether")) && type == Material.PORTAL) {
                            e.setCancelled(true);
                            pushAwayPlayer(player);
                            String message = (String) map.get(Ref.message);
                            Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                            return;
                        }
                        if ((boolean) map.get(("ender")) && type == Material.ENDER_PORTAL) {
                            e.setCancelled(true);
                            pushAwayPlayer(player);
                            String message = (String) map.get(Ref.message);
                            Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                        }
                    }
                }
            }
        }

    }

    // 向玩家当前面朝的相反方向推开
    private void pushAwayPlayer(Player player) {
        Vector backward = player.getLocation().getDirection().multiply(-0.5).setY(0.5);
        player.setVelocity(backward);
    }



}
