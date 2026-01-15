package com.maddyjace.worldrulesmanage.worldrule;
import java.util.List;
import java.util.Map;

public class WorldRuleField {

    public String worldName = null;

    // radius
    public int model  = 0;    // 半径模式
    public int radius = 0;   // 半径
    public List<String> xyz; // 坐标

    // globalRules / localRules
    public boolean liquidFlow     = false; // 液体流动
    public boolean leavesDecay    = false; // 树叶消失
    public boolean iceMelts       = false; // 冰融化
    public boolean waterFreezes   = false; // 水结冰
    public boolean iceSpread      = false; // 冰霜蔓延（特殊：冰霜行者）
    public boolean grassSpread    = false; // 草地蔓延
    public boolean myceliumSpread = false; // 菌丝蔓延
    public boolean fireSpread     = false; // 火焰蔓延
    public long weatherChange     = -1;    // 改变天气
    public long timeChange        = -1;    // 改变时间
    public Map<String, Object> creatureSpawn;     // 生成生物
    public Map<String, Object> entityExplode;     // 实体爆炸
    public Map<String, Object> entityChangeBlock; // 实体改变方块
    public Map<String, Object> blockBurn;         // 烧掉方块

    // playerRules
    public String permission = "worldrulesmanage.admin"; // 权限
    public Map<String, Object> lightTheBlock;       // 玩家点燃方块
    public Map<String, Object> usePail;                  // 玩家使用桶(如: 拿空桶装 水/岩浆)
    public Map<String, Object> hungerFrozen;             // 玩家饥饿
    public Map<String, Object> alwaysSatiated;           // 恢复玩家饥饿(等级: 20)
    public Map<String, Object> customInjured;            // 玩家受到自定义伤害
    public Map<String, Object> knockbackImmunity;        // 玩家被伤害击退
    public Map<String, Object> portal;                // 玩家使用任意传送门
    public Map<String, Object> blockBreak;               // 玩家破坏方块
    public Map<String, Object> blockPlace;               // 玩家放置方块
    public Map<String, Object> itemPickup;               // 玩家捡物品
    public Map<String, Object> itemDrop;                 // 玩家扔物品
    public Map<String, Object> useItem;                  // 玩家使用物品
    public Map<String, Object> interactEntity;           // 玩家互动实体
    public Map<String, Object> interactBlock;            // 玩家与方块互动
    public Map<String, Object> triggerBlock;             // 玩家触发方块

}
