package com.maddyjace.worldrulesmanage;

import com.maddyjace.worldrulesmanage.debug.Debug;
import com.maddyjace.worldrulesmanage.globalrules.*;
import com.maddyjace.worldrulesmanage.playerrules.*;
import com.maddyjace.worldrulesmanage.util.*;
import com.maddyjace.worldrulesmanage.worldrule.WorldDataLoad;
import org.bukkit.event.HandlerList;


public enum WorldRulesManage {
    INSTANCE; // 单例实例

    public void onEnable() {
        Config.INSTANCE.onEnable();                                         // 初始化 Config
        registerListeners();                                                // 初始化 监听器
        Language.Get.onEnable();                                            // 初始化 语言
        WorldDataLoad.INSTANCE.onEnable();                                  // 初始化 WorldDataLoad
        Message.INSTANCE = new Message((Config.INSTANCE.infoPeriod * 20L)); // 初始化 Message
    }

    public void onDisable() {
        unregisterListeners();              // 释放事件监听对象
        WorldDataLoad.INSTANCE.onDisable(); // 释放核心功能模块
        if (Message.INSTANCE != null) {     // 释放信息模块对象(实际是重新初始化)
            Message.INSTANCE = new Message(( Config.INSTANCE.infoPeriod * 20L));
        }
        Language.Get.onDisable();           // 释放语言模块对象
    }


    // 注册的监听器字段
    private Debug debug;
    private BlockBurn blockBurn;
    private CreatureSpawn creatureSpawn;
    private EntityChangeBlock entityChangeBlock;
    private EntityExplode entityExplode;
    private FireSpread fireSpread;
    private GrassSpread grassSpread;
    private IceMelts iceMelts;
    private IceSpread iceSpread;
    private LeavesDecay leavesDecay;
    private LiquidFlow liquidFlow;
    private MyceliumSpread myceliumSpread;
    private WaterFreezes waterFreezes;
    private WeatherChange weatherChange;
    private KeepInventory keepInventory;

    private AlwaysSatiated alwaysSatiated;
    private BlockBreak blockBreak;
    private BlockPlace blockPlace;
    private CustomInjured customInjured;
    private HungerFrozen hungerFrozen;
    private InteractBlock interactBlock;
    private InteractEntity interactEntity;
    private ItemDrop itemDrop;
    private ItemPickup itemPickup;
    private KnockbackImmunity knockbackImmunity;
    private LightTheBlock lightTheBlock;
    private Portal portal;
    private TriggerBlock triggerBlock;
    private UseItem useItem;
    private UsePail usePail;


    /** 注册监听器 */
    public void registerListeners() {
        Config c = Config.INSTANCE;

        if (c.entityExplodeName || c.blockExplodeName || c.entityChangeName || c.blockBurnName ||
            c.creatureSpawnName || c.blockBreakName || c.blockPlaceName || c.entityPickupItemName ||
            c.playerDropItemName || c.playerInteractRightClickName || c.playerPhysicalInteractName ||
            c.blockIgniteName || c.playerUseItemName || c.playerInteractEntityName || c.entityDamageName
        ) {
            debug = new Debug();
            Get.plugin().getServer().getPluginManager().registerEvents(debug, Get.plugin());
        }

        if (c.blockBurn) {
            blockBurn = new BlockBurn();
            Get.plugin().getServer().getPluginManager().registerEvents(blockBurn, Get.plugin());
        }
        if (c.creatureSpawn) {
            creatureSpawn = new CreatureSpawn();
            Get.plugin().getServer().getPluginManager().registerEvents(creatureSpawn, Get.plugin());
        }
        if (c.entityChangeBlock) {
            entityChangeBlock = new EntityChangeBlock();
            Get.plugin().getServer().getPluginManager().registerEvents(entityChangeBlock, Get.plugin());
        }
        if (c.entityExplode) {
            entityExplode = new EntityExplode();
            Get.plugin().getServer().getPluginManager().registerEvents(entityExplode, Get.plugin());
        }
        if (c.fireSpread) {
            fireSpread = new FireSpread();
            Get.plugin().getServer().getPluginManager().registerEvents(fireSpread, Get.plugin());
        }
        if (c.grassSpread) {
            grassSpread = new GrassSpread();
            Get.plugin().getServer().getPluginManager().registerEvents(grassSpread, Get.plugin());
        }
        if (c.iceMelts) {
            iceMelts = new IceMelts();
            Get.plugin().getServer().getPluginManager().registerEvents(iceMelts, Get.plugin());
        }
        if (c.iceSpread) {
            iceSpread = new IceSpread();
            Get.plugin().getServer().getPluginManager().registerEvents(iceSpread, Get.plugin());
        }
        if (c.leavesDecay) {
            leavesDecay = new LeavesDecay();
            Get.plugin().getServer().getPluginManager().registerEvents(leavesDecay, Get.plugin());
        }
        if (c.liquidFlow) {
            liquidFlow = new LiquidFlow();
            Get.plugin().getServer().getPluginManager().registerEvents(liquidFlow, Get.plugin());
        }
        if (c.myceliumSpread) {
            myceliumSpread = new MyceliumSpread();
            Get.plugin().getServer().getPluginManager().registerEvents(myceliumSpread, Get.plugin());
        }
        if (c.waterFreezes) {
            waterFreezes = new WaterFreezes();
            Get.plugin().getServer().getPluginManager().registerEvents(waterFreezes, Get.plugin());
        }
        if (c.weatherChange) {
            weatherChange = new WeatherChange();
            Get.plugin().getServer().getPluginManager().registerEvents(weatherChange, Get.plugin());
        }
        if (c.timeChange) {
            TimeChange.onEnable();
        }
        if (c.keepInventory) {
            keepInventory = new KeepInventory();
            Get.plugin().getServer().getPluginManager().registerEvents(keepInventory, Get.plugin());
        }

        if (c.alwaysSatiated) {
            alwaysSatiated = new AlwaysSatiated();
            Get.plugin().getServer().getPluginManager().registerEvents(alwaysSatiated, Get.plugin());
        }
        if (c.blockBreak) {
            blockBreak = new BlockBreak();
            Get.plugin().getServer().getPluginManager().registerEvents(blockBreak, Get.plugin());
        }
        if (c.blockPlace) {
            blockPlace = new BlockPlace();
            Get.plugin().getServer().getPluginManager().registerEvents(blockPlace, Get.plugin());
        }
        if (c.customInjured) {
            customInjured = new CustomInjured();
            Get.plugin().getServer().getPluginManager().registerEvents(customInjured, Get.plugin());
        }
        if (c.hungerFrozen) {
            hungerFrozen = new HungerFrozen();
            Get.plugin().getServer().getPluginManager().registerEvents(hungerFrozen, Get.plugin());
        }
        if (c.interactBlock) {
            interactBlock = new InteractBlock();
            Get.plugin().getServer().getPluginManager().registerEvents(interactBlock, Get.plugin());
        }
        if (c.interactEntity) {
            interactEntity = new InteractEntity();
            Get.plugin().getServer().getPluginManager().registerEvents(interactEntity, Get.plugin());
        }
        if (c.itemDrop) {
            itemDrop = new ItemDrop();
            Get.plugin().getServer().getPluginManager().registerEvents(itemDrop, Get.plugin());
        }
        if (c.itemPickup) {
            itemPickup = new ItemPickup();
            Get.plugin().getServer().getPluginManager().registerEvents(itemPickup, Get.plugin());
        }
        if (c.knockbackImmunity) {
            knockbackImmunity = new KnockbackImmunity();
            Get.plugin().getServer().getPluginManager().registerEvents(knockbackImmunity, Get.plugin());
        }
        if (c.lightTheBlock) {
            lightTheBlock = new LightTheBlock();
            Get.plugin().getServer().getPluginManager().registerEvents(lightTheBlock, Get.plugin());
        }
        if (c.portal) {
            portal = new Portal();
            Get.plugin().getServer().getPluginManager().registerEvents(portal, Get.plugin());
        }
        if (c.triggerBlock) {
            triggerBlock = new TriggerBlock();
            Get.plugin().getServer().getPluginManager().registerEvents(triggerBlock, Get.plugin());
        }
        if (c.useItem) {
            useItem = new UseItem();
            Get.plugin().getServer().getPluginManager().registerEvents(useItem, Get.plugin());
        }
        if (c.usePail) {
            usePail = new UsePail();
            Get.plugin().getServer().getPluginManager().registerEvents(usePail, Get.plugin());
        }
    }

    /** 注销监听器 */
    @SuppressWarnings({"ALL"})
    public void unregisterListeners() {
        if (blockBurn         != null) { HandlerList.unregisterAll(blockBurn);         blockBurn = null; }
        if (creatureSpawn     != null) { HandlerList.unregisterAll(creatureSpawn);     creatureSpawn = null; }
        if (entityChangeBlock != null) { HandlerList.unregisterAll(entityChangeBlock); entityChangeBlock = null; }
        if (entityExplode     != null) { HandlerList.unregisterAll(entityExplode);     entityExplode = null; }
        if (fireSpread        != null) { HandlerList.unregisterAll(fireSpread);        fireSpread = null; }
        if (grassSpread       != null) { HandlerList.unregisterAll(grassSpread);       grassSpread = null; }
        if (iceMelts          != null) { HandlerList.unregisterAll(iceMelts);          iceMelts = null; }
        if (iceSpread         != null) { HandlerList.unregisterAll(iceSpread);         iceSpread = null; }
        if (leavesDecay       != null) { HandlerList.unregisterAll(leavesDecay);       leavesDecay = null; }
        if (liquidFlow        != null) { HandlerList.unregisterAll(liquidFlow);        liquidFlow = null; }
        if (myceliumSpread    != null) { HandlerList.unregisterAll(myceliumSpread);    myceliumSpread = null; }
        if (waterFreezes      != null) { HandlerList.unregisterAll(waterFreezes);      waterFreezes = null; }
        if (weatherChange     != null) { HandlerList.unregisterAll(weatherChange);     weatherChange = null; }
        if (keepInventory     != null) { HandlerList.unregisterAll(keepInventory);     keepInventory = null; }

        if (alwaysSatiated    != null) { HandlerList.unregisterAll(alwaysSatiated);    alwaysSatiated = null; }
        if (blockBreak        != null) { HandlerList.unregisterAll(blockBreak);        blockBreak = null; }
        if (blockPlace        != null) { HandlerList.unregisterAll(blockPlace);        blockPlace = null; }
        if (customInjured     != null) { HandlerList.unregisterAll(customInjured);     customInjured = null; }
        if (hungerFrozen      != null) { HandlerList.unregisterAll(hungerFrozen);      hungerFrozen = null; }
        if (interactBlock     != null) { HandlerList.unregisterAll(interactBlock);     interactBlock = null; }
        if (interactEntity    != null) { HandlerList.unregisterAll(interactEntity);    interactEntity = null; }
        if (itemDrop          != null) { HandlerList.unregisterAll(itemDrop);          itemDrop = null; }
        if (itemPickup        != null) { HandlerList.unregisterAll(itemPickup);        itemPickup = null; }
        if (knockbackImmunity != null) { HandlerList.unregisterAll(knockbackImmunity); knockbackImmunity = null; }
        if (lightTheBlock     != null) { HandlerList.unregisterAll(lightTheBlock);     lightTheBlock = null; }
        if (portal            != null) { HandlerList.unregisterAll(portal);            portal = null; }
        if (triggerBlock      != null) { HandlerList.unregisterAll(triggerBlock);      triggerBlock = null; }
        if (useItem           != null) { HandlerList.unregisterAll(useItem);           useItem = null; }
        if (usePail           != null) { HandlerList.unregisterAll(usePail);           usePail = null; }
        // 注销 世界时间 调度器
        TimeChange.onDisable();

        if (debug             != null) { HandlerList.unregisterAll(debug);              debug = null;}

    }

}
