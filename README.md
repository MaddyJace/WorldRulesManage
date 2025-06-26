# WorldRulesManage Plugin Overview

**WorldRulesManage** is a professional plugin for managing world rules. It allows you to freely configure player behavior (e.g., blocking block breaking, block triggering, block interaction, etc.) and world behaviors (e.g., blocking fire spread, liquid flow, etc.) in specific worlds. You can also define radius-based rules using coordinates to restrict player or world actions within a certain area.  
**Use cases:** Server lobbies, spawn protection, and controlled world zones.
> English [README.md](./README.md)

> 中文简体 [CN-README.md](./CN-README.md)
---

## 📦 Plugin Features
- Supports global and radius-based world rules: prevent fire spread, prevent leaf decay, prevent block ignition (non-player), prevent entity block destruction, and block liquid flow (e.g., lava and water).
- Supports global and radius-based player rules: custom permission control, prevent block ignition, block breaking, block placing, item pickup/drop, bucket usage (e.g., collect water), hunger,  
  auto-restore hunger to full, prevent player damage, block all portals (overrides Nether/End), block Nether portal, block End portal, block specific item usage (configured by you),  
  block interaction with specific entities/blocks, block triggering of specific blocks.
- Radius rule shapes (for world and player): 2D Circle (ignores Y), 3D Sphere (includes Y), 2D Square (ignores Y), 3D Cube (includes Y), custom origin and radius values.
- Supports blacklist/whitelist configuration
- Supports multiple worlds
- Supports auto-hot-reload of configuration files

---

# ⚙️ WorldRulesManage Configuration Guide
> [config.yml](src/main/resources/config.yml)

> [world.yml](src/main/resources/world.yml)

> [radius.yml](src/main/resources/radius.yml)

> [message.yml](src/main/resources/message.yml)

### ✅ `worldName: <world>`             # Name of the world (e.g., world)
### ✅ `globalRules:`                   # Rules applied to the world, regardless of player permissions
- `flameSpread: true`                  # Prevent fire from spreading
- `leavesDecay: true`                  # Prevent leaf decay
- `blockIgnite: true`                  # Prevent block ignition
- `entityBlockBreak: true`             # Prevent entities from breaking blocks
- `liquidFlow: true`                   # Prevent liquid flow

### ✅ `playerRules:`                   # Rules applied to players without permission
- `permission: "wm.world"`             # Permission node
- `blockIgnite: true`                  # Prevent players from igniting blocks
- `blockBreak: true`                   # Prevent players from breaking blocks
- `blockPlace: true`                   # Prevent players from placing blocks
- `itemPickup: false`                  # Prevent players from picking up items
- `itemDrop: false`                    # Prevent players from dropping items
- `usePail: true`                      # Prevent players from using buckets (e.g., to collect water)
- `food: true`                         # Prevent hunger
- `foodSatiety: true`                  # Restore hunger to full
- `playerDamage: true`                 # Prevent player damage
- `portalAll: false`                   # Prevent all portal access (overrides portalNether and portalEnder)
- `portalNether: false`                # Prevent Nether portal access
- `portalEnder: false`                 # Prevent End portal access

######
- `🎯playerUseItem:`                   # Block item usage
    - `type:` `BLACKLIST` / `WHITELIST`
    - `list:`
        - "EGG"                           # Egg
        - "FLINT_AND_STEEL"              # Flint and Steel

######
- `🧍PlayerInteractEntity:`            # Block entity interaction
    - `type:` `BLACKLIST` / `WHITELIST`
    - `list:`
        - "ITEM_FRAME"                # Item Frame
        - "PAINTING"                  # Painting
        - "ARMOR_STAND"               # Armor Stand

######
- `🧱PlayerInteractBlock:`             # Block block interaction
    - `type:` `BLACKLIST` / `WHITELIST`
    - `list:`
        - "LEVER"                     # Lever
        - "FURNACE"                   # Furnace

######
- `🎛️PlayerTriggerBlock:`             # Block block triggering
    - `type:` `BLACKLIST` / `WHITELIST`
    - `list:`
        - "SOIL"                      # Farmland
        - "TRIPWIRE"                  # Tripwire

---

### `This option only takes effect in radius.yml`
- `✅settingsRadius:`
    - `type:` `2DRange`, `3DRange`, `2DCube`, `3DCube`
    - `xyz:`       # Starting coordinates (e.g., 0 0 0)
    - `radius:`    # Radius in blocks

## 📌 Case Sensitivity Warning

> ⚠️ **Important:** All configuration keys are case-sensitive. Misuse of uppercase/lowercase may result in errors or ignored settings.

---

## 🛠 Recommended Practices
- When using TNT near the radius edge, explosions may affect blocks just outside the protected area. To avoid this, expand the radius by 7 blocks for edge safety.
- Remove or comment out unused configuration sections to keep your setup clean and readable.

---
