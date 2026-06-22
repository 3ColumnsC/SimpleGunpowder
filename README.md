# Simple Gunpowder

Adds gunpowder recipes and villager trades, including a bulk recipe for large amounts of gunpowder — ideal for fireworks and TNT, without relying on creeper farms or mob grinding.

![Fabric](https://img.shields.io/badge/Fabric-Supported-green)
![NeoForge](https://img.shields.io/badge/NeoForge-Supported-green)
![License](https://img.shields.io/badge/License-MIT-blue)

---

<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782139737/small-medium-large_rzsgdk.png" alt="Small, Medium & Large Gunpowder Recipes" width="900">
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1782139737/industrial_aiwhfk.png" alt="Industrial Gunpowder Recipe" width="900">

---

## ⚡ Features

### 🧪 Crafting Recipes

Four configurable gunpowder recipes are included:

| Recipe     | Output       |
| ---------- | ------------ |
| Small      | 4 Gunpowder  |
| Medium     | 6 Gunpowder  |
| Large      | 10 Gunpowder |
| Industrial | 32 Gunpowder |

Each recipe can be individually enabled or disabled through the configuration file.

### 🧑‍🌾 Villager Trades

* Novice Fletcher & Cleric villagers can sell gunpowder.
* Trade cost and amount are configurable.

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
  "enableTrades": true,
  "tradeEmeraldCost": 2,
  "tradeGunpowderAmount": 10
}
```

---

## 📦 Requirements

### Fabric

* Fabric API
* (+26.X) Java 25 or newer

### NeoForge

* (+26.X) Java 25 or newer

---

## 📜 License

This project is licensed under the MIT License.
