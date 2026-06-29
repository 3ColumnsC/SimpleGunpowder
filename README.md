# Simple Gunpowder

Adds gunpowder recipes and villager trades, including bulk recipes for large amounts of gunpowder — ideal for fireworks and TNT, without relying on creeper farms or mob grinding.

![Fabric](https://img.shields.io/badge/Fabric-Supported-green)
![NeoForge](https://img.shields.io/badge/NeoForge-Supported-green)
![Forge](https://img.shields.io/badge/Forge-Supported-green)
![License](https://img.shields.io/badge/License-MIT-blue)

---

## 📀 26.2+
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782370733/sulfur_vj2ywx.jpg" alt="Sulfur Gunpowder Recipes" width="900">

## 💿 Available for All Versions
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782370686/small-medium-large_rzsgdk_uyiuf0.jpg" alt="Small, Medium & Large Gunpowder Recipes" width="900">
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782370648/industrial_aiwhfk_vem9a7.jpg" alt="Industrial Gunpowder Recipe" width="900">

---

## ⚡ Features

### 🧪 Crafting Recipes

Four configurable gunpowder recipes are included:

| Recipe          | Output       |
| --------------- | ------------ |
| Small           | 4 Gunpowder  |
| Medium          | 6 Gunpowder  |
| Large           | 10 Gunpowder |
| Large Sulfur    | 14 Gunpowder |
| Industrial      | 32 Gunpowder |
| Potent Sulfur   | 48 Gunpowder |

Each recipe can be individually enabled or disabled through the configuration file.

### 🧑‍🌾 Villager Trades

Novice Fletcher and Cleric villagers can offer the following trades:

| Wants | Gives |
|-------|-------|
| 2 Emeralds | 10 Gunpowder |
| 1 Sulfur + 1 Cinnabar | 6 Gunpowder |

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
  "enableLargeSulfurRecipe": true,
  "enablePotentSulfurRecipe": true
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
