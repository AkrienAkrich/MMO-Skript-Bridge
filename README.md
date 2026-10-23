# MMOSkriptBridge
**by AkrienDiverse**

I built MMOSkriptBridge to connect MMOItems and MythicMobs to Skript, so you can manage items, identify mobs, and cast skills directly from your scripts.

**Current build: 1.1.0-beta.3.** This is a beta, not a fully tested stable release.

[Download the beta JAR](dist/MMOSkriptBridge-1.1.0-beta.3.jar) · [Report a problem](https://github.com/AkrienAkrich/MMO-Skript-Bridge/issues)

## Features

- Read MMOItems item IDs and types, and match items by type and ID.
- Generate, give, take, and count MMOItems items.
- Identify MythicMobs entities and read their internal IDs.
- Check whether a MythicMobs skill exists.
- Cast skills at entities or locations, with optional triggers.
- Preserve the original bridge syntax, including the legacy delay/repeat form.
- Load MMOItems and MythicMobs integrations independently.

## Installation

1. Stop the server.
2. Remove old **MMOItems-Skript**, **MythicMobs-Skript-Bridge**, or **AkrienSkriptBridge** JARs.
3. Put the new JAR in `plugins/`.
4. Install Skript and the plugins needed for your integration.
5. Start the server and check the console integration status.

Do not install this plugin alongside its predecessors. Use a full restart when replacing the JAR.

| Dependency | Purpose |
| --- | --- |
| Skript | Required |
| MMOItems | Item features |
| MythicLib | Required by MMOItems |
| MythicMobs | Mob and skill features |

Dependencies are installed separately and are not bundled here.

## Examples

Replace the sample type, item ID, and skill name with entries from your own configuration. These examples illustrate the registered syntax; they have not been validated in a server test suite.

```vb
command /bridge-item:
    permission: mmoskriptbridge.example
    executable by: players
    trigger:
        give mmoitem "SWORD" "EXAMPLE_SWORD" with amount 1 to player
        set {_count} to amount of mmoitem "SWORD" "EXAMPLE_SWORD" in player
        send "Matching items: %{_count}%" to player

command /bridge-skill:
    permission: mmoskriptbridge.example
    executable by: players
    trigger:
        if mythic skill "ExampleSkill" exists:
            cast mythic skill "ExampleSkill" from player at player
```

Additional syntax:

```vb
set {_id} to mmoitem id of player's tool
set {_type} to mmoitem type of player's tool
set {_item} to mmoitem "SWORD" "EXAMPLE_SWORD"
take amount 1 mmoitem "SWORD" "EXAMPLE_SWORD" from player
cast mythic skill "ExampleSkill" from player with trigger player at location of player
```

The `take` effect removes up to the requested quantity; it does not require a complete quantity before removing items. Check the count first for purchases or crafting. Give/take quantities of zero or less are currently clamped to one.

## Compatibility and validation

Compilation passed against Spigot API 1.20.1 and the supplied Skript 2.16.1 JAR, targeting Java 17 bytecode. Use the Java version required by your server and dependencies.

I have confirmed that both integrations initialize on **Purpur 1.21.11** with:

| Plugin | Build used |
| --- | --- |
| Skript | 2.16.1 |
| MMOItems | 6.10.1-SNAPSHOT (file label 6.10.1-59) |
| MythicLib | 1.7.1-SNAPSHOT (file label 1.7.1-106) |
| MythicMobs | 5.13.1-SNAPSHOT (file label 6086) |

A successful startup is not a full functional test. Item operations, skill targeting, triggers, delays, and repetitions still need server testing.

**Minecraft 1.20.x compatibility is not verified.** The plugin declares API 1.20, but supplied Skript 2.16.1 declares API 1.21. Do not interpret the metadata or compilation target as support for every server version.

## Reporting issues

Include your server version, Java version, dependency versions, a minimal script, and the relevant error log. Remove credentials, tokens, and private server details.

## Source and authorship

I lost the original source files, so I recovered the bridge classes from my JARs using CFR. I used AI assistance to combine the addons, fix recovery-related compilation errors, and prepare the console branding and documentation.

## License

I am releasing this code under the [MIT License](LICENSE). You may use, modify, and redistribute it, including commercially, provided you retain the copyright and license notice.

---

## По-русски

Я сделал MMOSkriptBridge для связи Skript с MMOItems и MythicMobs: выдача, изъятие и подсчёт предметов, чтение ID и типа, определение мобов и вызов скиллов.

Установите JAR в `plugins/`, удалите старые отдельные мосты и AkrienSkriptBridge, затем полностью перезапустите сервер. Skript обязателен; две интеграции подключаются независимо. Для MMOItems также нужен MythicLib.

Это бета-сборка. Я проверил загрузку интеграций на Purpur 1.21.11, но полное функциональное тестирование ещё не завершено. Поддержку 1.20.x пока не подтверждаю.

Код выпускаю под MIT: его можно использовать, менять и распространять, в том числе в коммерческих проектах, сохраняя уведомление об авторстве и текст лицензии.
