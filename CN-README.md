# WorldRulesManage 插件概述

**WorldRulesManage** 专业的世界规则管理插件，允许您在特定世界自由的配置玩家的行为(如阻止: 破坏方块，触发方块，交互方块，等) 或者 世界的行为(如阻止: 火焰传播，液体流动，等)的规则。您还可以在特定世界通过坐标定义半径规则，在半径范围内阻止玩家的行为或世界的行为。适用场景：服务器大厅保护 和 世界出生点保护。
> English [README.md](./README.md)

> 中文简体 [CN-README.md](./CN-README.md)
---

## 📦 插件功能
- 支持全局和半径规则(针对世界)：防止火焰传播，防止树叶自然衰减，防止点燃方块(不含玩家)，阻止实体破坏方块，阻止液体流动(如:岩浆和水)。
- 支持全局和半径规则(针对玩家)：自定义权限，防止点燃方块，阻止破坏方块，阻止放置方块，阻止玩家捡物品，阻止玩家扔物品，阻止玩家使用桶(如: 装水)，阻止玩家饥饿，
  恢复玩家的饥饿度为满，阻止玩家受伤，阻止玩家进入任何传送门(开启后阻止地狱和末地会失效)，阻止玩家进入地狱传送门，是否阻止玩家进入末地传送门，阻止玩家使用物品(需要您配置阻止的物品)，
  阻止玩家实体交互(需要您配置阻止的实体)，阻止玩家与方块交互(需要您配置阻止的方块)，阻止玩家触发方块(需要您配置阻止的方块)。
- 支持半径规则(针对世界和玩家)：二维圆形(无Y轴)，三维圆形(有Y轴)，二维立方(无Y轴)，三维立方(有Y轴)，自定义初始坐标，自定义半径范围。
- 支持配置列表 黑名单/白名单
- 支持配置多个世界
- 支持配置文件自动热重载

---

# ⚙️ WorldRulesManage 配置指南
> [config.yml](src/main/resources/zh-cn/zh-cn_config.yml)

> [world.yml](src/main/resources/zh-cn/zh-cn_world.yml)

> [radius.yml](src/main/resources/zh-cn/zh-cn_radius.yml)

> [message.yml](src/main/resources/zh-cn/zh-cn_message.yml)
### ✅`worldName: <world>` # 世界的名称(如: world)。
### ✅`globalRules:` # 针对世界，即使玩家拥有权限。
- `flameSpread: true`      # 防止火焰传播
- `leavesDecay: true`      # 是否防止树叶自然消失
- `blockIgnite: true`      # 是否防止点燃方块
- `entityBlockBreak: true` # 是否阻止实体破坏方块
- `liquidFlow: true`       # 是否阻止液体流动

### ✅`playerRules:` # 针对玩家，适用于没有权限的玩家的规则。
- `permission: "wm.world"`  权限
- `blockIgnite: true`      # 是否防止玩家点燃方块
- `blockBreak: true`       # 是否阻止玩家破坏方块
- `blockPlace: true`       # 是否阻止玩家放置方块
- `itemPickup: false`      # 是否阻止玩家捡物品
- `itemDrop: false`        # 是否阻止玩家扔物品
- `usePail: true`          # 是否阻止玩家使用桶(如: 装水)
- `food: true`             # 是否阻止玩家饥饿
- `foodSatiety: true`      # 是否恢复玩家的饥饿度为满
- `playerDamage: true`     # 是否阻止玩家受伤
- `portalAll: false`       # 是否阻止玩家进入任何传送门(开启此选项，会覆盖portalNether和portalEnder)
- `portalNether: false`    # 是否阻止玩家进入地狱传送门
- `portalEnder: false`     # 是否阻止玩家进入末地传送门
######
- `🎯playerUseItem:` # 阻止玩家使用物品
    - `type: ` `BLACKLIST`&`WHITELIST`
    - `list:`
        - "EGG"             # 鸡蛋
        - "FLINT_AND_STEEL" # 打火石
######
- `🧍PlayerInteractEntity:` # 阻止玩家交互实体
    - `type: ` `BLACKLIST`&`WHITELIST`
    - `list:`
        - "ITEM_FRAME"  # 物品展示框
        - "PAINTING"    # 画
        - "ARMOR_STAND" # 盔甲架
######
- `🧱PlayerInteractBlock:` # 阻止玩家交互方块
    - `type: ` `BLACKLIST`&`WHITELIST`
    - `list:`
        - "LEVER"   # 拉杆
        - "FURNACE" # 熔炉
######
- `🎛️PlayerTriggerBlock:` # 阻止玩家触发方块
    - `type: ` `BLACKLIST`&`WHITELIST`
    - `list:`
        - "SOIL"     # 耕地
        - "TRIPWIRE" # 绊线
---
### `此选项只在 radius.yml 中生效`
- `✅settingsRadius:`
    - `type: ` `2DRange`, `3DRange`, `2DCube`, `3DCube`
    - `xyz:`     # 起点坐标(例如，0 0 0)
    - `radius: ` # 半径（以方块为单位）

## 📌 对大小写敏感的警告

> ⚠️ **重要:** 所有配置键都是区分大小写的。 大小写错误可能会导致错误或忽略设置。

---

## 🛠 推荐实践
- 当在半径边缘附近使用TNT时，爆炸会导致破坏半径的边缘方块，除非爆炸在半径内发生，将半径增加 +7 格，以防止边缘相关的问题。
- 移除或注释掉未开启的部分配置项，以保持配置的整洁和易读性。
---
