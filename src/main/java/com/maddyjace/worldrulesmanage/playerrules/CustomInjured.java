package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 玩家受伤 事件监听器 */
public class CustomInjured implements Listener {

    // 取消受伤
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {

        // 判断是否为玩家
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();                        // 玩家对象
        World world = player.getWorld();                               // 世界对象
        Location current = player.getLocation();                       // 坐标位置

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).customInjured;
                if (WorldUtil.conJud(map, enable)) {
                    damageCause(e, player, map);
                    return;
                }
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getLocalData().get(world.getName()).permission;
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).customInjured;
                if (WorldUtil.inRadius(world, current)) {
                    if (WorldUtil.conJud(map, enable)) {
                        damageCause(e, player, map);
                    }
                }
            }
        }

    }

    private void damageCause(EntityDamageEvent e,Player player, Map<String, Object> map) {
        String message = (String) map.get(Ref.message);
        if ((boolean) map.get(damageCauseEnable)) {
            // 任意伤害
            if ((boolean) map.get(allCause)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }

            // 自然接触伤害（比如仙人掌）
            if (e.getCause() == EntityDamageEvent.DamageCause.CONTACT && (boolean) map.get(contact)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 实体攻击
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && (boolean) map.get(entityAttack)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 实体扫击（像剑扫到周围）
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK && (boolean) map.get(entitySweepAttack)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 射弹伤害（箭、雪球等）
            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && (boolean) map.get(projectile)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 窒息
            if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION && (boolean) map.get(suffocation)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 跌落
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && (boolean) map.get(fall)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 火焰
            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE && (boolean) map.get(fire)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 火焰持续伤害
            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK && (boolean) map.get(fireTick)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 熔岩
            if (e.getCause() == EntityDamageEvent.DamageCause.LAVA && (boolean) map.get(lava)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 溺水
            if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING && (boolean) map.get(drowning)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 方块爆炸
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && (boolean) map.get(blockExplosion)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 实体爆炸（TNT、爪子怪等）
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION && (boolean) map.get(entityExplosion)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 空虚（掉落世界边界）
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID && (boolean) map.get(voidDamage)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 闪电
            if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING && (boolean) map.get(lightning)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 自杀
            if (e.getCause() == EntityDamageEvent.DamageCause.SUICIDE && (boolean) map.get(suicide)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 饥饿
            if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION && (boolean) map.get(starvation)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 中毒
            if (e.getCause() == EntityDamageEvent.DamageCause.POISON && (boolean) map.get(poison)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 魔法
            if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC && (boolean) map.get(magic)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 凋零
            if (e.getCause() == EntityDamageEvent.DamageCause.WITHER && (boolean) map.get(wither)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 下落方块
            if (e.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK && (boolean) map.get(fallingBlock)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 荆棘
            if (e.getCause() == EntityDamageEvent.DamageCause.THORNS && (boolean) map.get(thorns)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 龙息
            if (e.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH && (boolean) map.get(dragonBreath)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 自定义
            if (e.getCause() == EntityDamageEvent.DamageCause.CUSTOM && (boolean) map.get(custom)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 飞入墙体
            if (e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL && (boolean) map.get(flyIntoWall)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 热地板
            if (e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR && (boolean) map.get(hotFloor)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            // 压迫
            if (e.getCause() == EntityDamageEvent.DamageCause.CRAMMING && (boolean) map.get(cramming)) {
                e.setCancelled(true);
                Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                return;
            }
            return;
        }

        // 智能分支
        if (!(
                e.getCause() == EntityDamageEvent.DamageCause.SUICIDE ||
                        e.getCause() == EntityDamageEvent.DamageCause.CUSTOM ||
                        e.getFinalDamage() >= ((Player) e.getEntity()).getHealth())
        )
        {
            e.setCancelled(true);
            Message.INSTANCE.sendMessage(player.getUniqueId(), message);
        }

    }

}
