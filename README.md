# Simple Gunpowder

Adds gunpowder recipes, villager trades, piglin bartering and End City loot, including bulk recipes for large amounts of gunpowder — ideal for fireworks and TNT, without relying on creeper farms or mob grinding.

8 Progression-Ready Overworld & Nether recipes.

---

## 📀 26.2+
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782370733/sulfur_vj2ywx.jpg" alt="Sulfur Gunpowder Recipes" width="900">

## 💿 Available for All Versions
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782370686/small-medium-large_rzsgdk_uyiuf0.jpg" alt="Small, Medium & Large Gunpowder Recipes" width="900">
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782370648/industrial_aiwhfk_vem9a7.jpg" alt="Industrial Gunpowder Recipe" width="900">
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1783753640/sgImages_hxqagc.jpg" alt="Nether Recipes" width="900">

---

## ⚡ Features

### 🧪 Crafting Recipes

Eight configurable gunpowder recipes are included:

| Overworld Recipes | Output       |
|-------------------| ------------ |
| Small             | 4 Gunpowder  |
| Medium            | 6 Gunpowder  |
| Large             | 10 Gunpowder |
| Industrial        | 32 Gunpowder |

| Overworld Sulfur Recipes | Output       |
|--------------------------| ------------ |
| Refined Sulfur           | 14 Gunpowder |
| Potent Sulfur            | 48 Gunpowder |

| Nether Recipes | Output       |
|----------------| ------------ |
| Nether Small   | 8 Gunpowder  |
| Nether Medium  | 12 Gunpowder |

Each recipe can be individually enabled or disabled through the configuration file.

### 🧑‍🌾 Villager Trades

Novice Fletcher and Cleric villagers can offer the following trades:

| Wants | Gives |
|-------|-------|
| 2 Emeralds | 10 Gunpowder |
| 1 Sulfur + 1 Cinnabar | 6 Gunpowder |

### 🐷 Piglin Bartering

Piglins have a chance to barter gunpowder (8–16) when given a gold ingot.

### 🏛️ End City Chests

End city treasure chests have a chance to contain gunpowder (48–64).

---

## ⚙️ Configuration

After launching the game once, a configuration file will be generated:

```text
config/simplegunpowder.json
```

Available options:

```json
{
  "enableSmallCrafting": true,
  "enableMediumCrafting": true,
  "enableLargeCrafting": true,
  "enableIndustrialCrafting": true,
  "enableRefinedSulfurRecipe": true,
  "enablePotentSulfurRecipe": true,
  "enableNetherSmallRecipe": true,
  "enableNetherMediumRecipe": true
}
```

---

## 📦 Requirements

### Fabric

* Fabric API
* (+26.X) Java 25 or newer

### NeoForge

* (+26.X) Java 25 or newer

### Forge

* Java 17 or newer

---

## 📜 License

This project is licensed under the MIT License.
