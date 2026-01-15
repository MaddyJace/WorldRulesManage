package com.maddyjace.worldrulesmanage.util;

import com.maddyjace.worldrulesmanage.worldrule.WorldDataLoad;

/**
 * className: QuickReference <p>
 * Ref 是 QuickReference 的简写。
 */
public class Ref {
    public final static WorldDataLoad wdl = WorldDataLoad.INSTANCE;

    /** 常用静态字段，为了防止开销通常从这调用 */
    private final static String globalRules = "globalRules";
    private final static String liquidFlow = "liquidFlow";
    private final static String leavesDecay = "leavesDecay";
    private final static String iceMelts = "iceMelts";
    private final static String waterFreezes = "waterFreezes";
    private final static String iceSpread = "iceSpread";
    private final static String grassSpread = "grassSpread";
    private final static String myceliumSpread = "myceliumSpread";
    private final static String fireSpread = "fireSpread";
    private final static String weatherChange = "weatherChange";
    private final static String timeChange = "timeChange";
    private final static String entityExplode = "entityExplode";
    private final static String entityChangeBlock = "entityChangeBlock";
    private final static String blockBurn = "blockBurn";
    private final static String creatureSpawn = "creatureSpawn";

    private final static String playerRules = "playerRules";
    private final static String permission = "permission";

    public final static String enable = "enable";
    public final static String message = "message";
    public final static String checkMode = "checkMode";
    public final static String itemList = "itemList";
    public final static String blockList = "blockList";
    public final static String entityList = "entityList";
    public final static String list = "list";

    public final static String damageCauseEnable = "damageCauseEnable";
    public final static String allCause = "allCause";
    public static final String contact = "contact";
    public static final String entityAttack = "entityAttack";
    public static final String entitySweepAttack = "entitySweepAttack";
    public static final String projectile = "projectile";
    public static final String suffocation = "suffocation";
    public static final String fall = "fall";
    public static final String fire = "fire";
    public static final String fireTick = "fireTick";
    public static final String lava = "lava";
    public static final String drowning = "drowning";
    public static final String blockExplosion = "blockExplosion";
    public static final String entityExplosion = "entityExplosion";
    public static final String voidDamage = "voidDamage";
    public static final String lightning = "lightning";
    public static final String suicide = "suicide";
    public static final String starvation = "starvation";
    public static final String poison = "poison";
    public static final String magic = "magic";
    public static final String wither = "wither";
    public static final String fallingBlock = "fallingBlock";
    public static final String thorns = "thorns";
    public static final String dragonBreath = "dragonBreath";
    public static final String custom = "custom";
    public static final String flyIntoWall = "flyIntoWall";
    public static final String hotFloor = "hotFloor";
    public static final String cramming = "cramming";

}
