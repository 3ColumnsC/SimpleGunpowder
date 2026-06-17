# Simple Gunpowder

A lightweight Minecraft mod that adds alternative gunpowder acquisition methods through crafting recipes and villager trades.

Designed for survival worlds that need a balanced way to obtain gunpowder without relying entirely on creeper farms or excessive mob grinding.

![Fabric](https://img.shields.io/badge/Fabric-Supported-green)
![NeoForge](https://img.shields.io/badge/NeoForge-Supported-green)
![License](https://img.shields.io/badge/License-MIT-blue)

---

<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1781518288/small-medium_yk5mn2.png" alt="Small & Medium Gunpowder Recipes" width="900">
<img src="https://res.cloudinary.com/dbtdewiqk/image/upload/v1781518288/large_kdbnfw.png" alt="Large Gunpowder Recipe" width="900">

---

## ⚡ Features

### 🧪 Crafting Recipes

Three configurable gunpowder recipes are included:

| Recipe | Output       |
| ------ | ------------ |
| Small  | 4 Gunpowder  |
| Medium | 6 Gunpowder  |
| Large  | 10 Gunpowder |

Each recipe can be individually enabled or disabled through the configuration file.

### 🧑‍🌾 Villager Trades

* Novice Fletcher villagers can sell gunpowder.
* Novice Cleric villagers can sell gunpowder.
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
