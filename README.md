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

| Recipe          | Output       |
| --------------- | ------------ |
| Small           | 4 Gunpowder  |
| Medium          | 6 Gunpowder  |
| Large           | 10 Gunpowder |
| Large Sulfur    | 14 Gunpowder |
| Industrial      | 32 Gunpowder |
| Potent Sulfur   | 48 Gunpowder |

Each recipe can be individually enabled or disabled through the configuration file.

| Recipe          | Ingredients                                             | Output       |
| --------------- | ------------------------------------------------------- | ------------ |
| Small           | 3 Coal                                                  | 4 Gunpowder  |
| Medium          | 2 Coal, 2 Sand                                          | 6 Gunpowder  |
| Large           | 2 Coal, 2 Sand, 2 Redstone                              | 10 Gunpowder |
| Large Sulfur    | 2 Coal, 1 Sulfur (or Spike), 1 Cinnabar                 | 14 Gunpowder |
| Industrial      | 1 Coal Block, 1 Sand, 1 Redstone                        | 32 Gunpowder |
| Potent Sulfur   | 1 Potent Sulfur, 1 Coal Block, 1 Cinnabar               | 48 Gunpowder |

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

---

## 📜 License

This project is licensed under the MIT License.
