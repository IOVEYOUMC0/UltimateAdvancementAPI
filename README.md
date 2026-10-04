<h1 align="center">UltimateAdvancementAPI</h1>

<p align="center">
  <img src="https://img.shields.io/badge/License-LGPL--3.0--or--later-orange" alt="License: LGPL-3.0-or-later">
  <img src="https://img.shields.io/badge/Minecraft-1.21.5%20%2F%201.21.11%20%2F%2026.x-3fb950" alt="Minecraft 1.21.5 / 1.21.11 / 26.x">
  <img src="https://img.shields.io/badge/API-2.8.0-blue" alt="API 2.8.0">
  <img src="https://img.shields.io/badge/fork%20of-frengor%2FUltimateAdvancementAPI-007ec6" alt="Fork of frengor/UltimateAdvancementAPI">
</p>

<p align="center"><i>A powerful API to create custom advancements for your Minecraft server, forked here with NMS variants for Minecraft 1.21.5, 1.21.11 and 26.x.</i></p>

---

## About

A powerful API to create custom advancements for your minecraft server.

This repository is a **fork** of [frengor](http://frengor.com)'s
[UltimateAdvancementAPI](https://github.com/frengor/UltimateAdvancementAPI), whose authors are **frengor**
and **EscanorTargaryen**. The fork keeps the older 1.21.x NMS layers and carries the 1.21.5 (`v1_21_R4`),
1.21.11 (`v1_21_R7`) and 26.x (`v26_1_R2`, `v26_2_R1`, `v26_3_R1`) variants used by this server family. It
follows the upstream license, [LGPL-3.0-or-later](https://www.gnu.org/licenses/lgpl-3.0.txt).

The links below point at the **upstream** project, not at this fork; the fork does not run its own CI,
Javadoc or wiki.

> **3.0.0 Beta** is available on the [`main-3.0.0` branch](https://github.com/frengor/UltimateAdvancementAPI/tree/main-3.0.0). Download the beta from Modrinth on Hangar (links below).  
> The Javadoc for the beta is published [here](https://frengor.com/javadocs/UltimateAdvancementAPI/3.0.0-beta-1/).

**Modrinth Page:** <https://modrinth.com/plugin/ultimateadvancementapi>  
**Spigot Page:** <https://www.spigotmc.org/resources/95585/>  
**Hangar Page:** <https://hangar.papermc.io/DevHeim/UltimateAdvancementAPI>  
**UltimateAdvancementGenerator:** <https://escanortargaryen.dev/UltimateAdvancementGenerator/>  
**Discord:** <https://discord.gg/BMg6VJk5n3>  
**Official Wiki:** <https://github.com/frengor/UltimateAdvancementAPI/wiki/>  
**Javadoc:** <https://frengor.com/javadocs/UltimateAdvancementAPI/latest/>  
**Jenkins:** <https://jenkins.frengor.com/job/UltimateAdvancementAPI/>

**Get it with maven:**
```xml
<repositories>
    <repository>
        <id>fren_gor</id>
        <url>https://nexus.frengor.com/repository/public/</url>
    </repository>
</repositories>
```   
```xml
<dependency>
    <groupId>com.frengor</groupId>
    <artifactId>ultimateadvancementapi</artifactId>
    <version>2.8.0</version>
    <scope>provided</scope>
</dependency>
```

#### Example Plugin:

An example of plugin using UltimateAdvancementAPI can be found [here](https://github.com/DevHeim-space/UltimateAdvancementAPI-Showcase).

More examples by the community can be found in the `showcase` forum on [Discord](https://discord.gg/BMg6VJk5n3).

#### Automatic advancement layout

Pass `true` when registering a tab to calculate vanilla-style tree coordinates from each
advancement's parent relationship:

```java
tab.registerAdvancements(root, true, advancements);
// Set overload:
tab.registerAdvancements(root, advancementsSet, true);
```

The existing overloads keep the coordinates supplied through `AdvancementDisplay`.

#### Test Plugin:

The plugin used for tests can be found [here](https://github.com/frengor/UltimateAdvancementAPI-Tests).

## Contributing

Feel free to open issues or pull requests. Feature requests can be done opening an issue, the `enhancement` tag will be applied by maintainers.

For pull requests, open them towards the `dev` branch, as the `main` branch is only for releases. Make sure to allow edits by maintainers.
Also, please use the formatting style settings present under `.idea/codeStyles` folder.

## Required Java version

Currently, the project is compiled for Java 16, although the minimum required Java version might change in future releases.

> We consider changing the minimum required Java version a breaking change, so DO NOT expect it to be frequently modified.

In order to compile the code you must be using (at least) the Java version required by the last Minecraft version, since the project uses NMS.

## License

This project is licensed under the [GNU Lesser General Public License v3.0 or later](https://www.gnu.org/licenses/lgpl-3.0.txt).

This repository is a fork of [frengor/UltimateAdvancementAPI](https://github.com/frengor/UltimateAdvancementAPI)
and inherits its LGPL-3.0-or-later terms. The `LICENSE` file holds the full GPL-3.0 text and
`COPYING.LESSER` the LGPL-3.0 additional permissions, which together make up LGPL-3.0; the `LGPL` file is the
same LGPL text under its short name.

## Credits

This repository is a fork of [UltimateAdvancementAPI](https://github.com/frengor/UltimateAdvancementAPI),
originally by **[frengor](http://frengor.com)** together with **EscanorTargaryen**. The fork adds the
1.21.5 / 1.21.11 / 26.x NMS variants and follows the upstream license, **LGPL-3.0-or-later**.

UltimateAdvancementAPI has been made by [fren_gor](https://github.com/frengor) and [EscanorTargaryen](https://github.com/EscanorTargaryen).  
The API uses the following libraries:
  * [EventManagerAPI](https://github.com/frengor/EventManagerAPI) (released under Apache-2.0 license) to handle events
  * [Libby](https://github.com/AlessioDP/libby) (released under MIT license) to handle dependencies at runtime
  * [CommandAPI](https://github.com/CommandAPI/CommandAPI) (released under MIT license) to add commands to the plugin version of the API
  * [HikariCP](https://github.com/brettwooldridge/HikariCP) (released under Apache-2.0 license) to connect to MySQL databases
  * [Config-Updater](https://github.com/tchristofferson/Config-Updater) (released under MIT license) to update the configuration in the plugin version of the API
  * [bStats](https://bstats.org/) (the Java library is released under MIT license) to collect usage data (which can be found [here](https://bstats.org/plugin/bukkit/UltimateAdvancementAPI/12593)) about the plugin version of the API
