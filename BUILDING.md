# Building MMOSkriptBridge

## Tools and dependencies

- Python 3 and Java 17 or newer.
- Eclipse ECJ 3.39.0 compiler JAR.
- Spigot API 1.20.1 shaded JAR.
- Skript 2.16.1 JAR.

These are compile-time inputs; do not place them in this repository. Runtime MMOItems, MythicLib and MythicMobs are also not bundled.

The current build script uses ECJ because it can read the supplied newer dependency class files while targeting Java 17. Compiling is not a server compatibility test.

## Command

From the repository root:

```sh
python build.py --compiler /path/to/ecj-3.39.0.jar --bukkit /path/to/spigot-api-1.20.1-shaded.jar --skript /path/to/Skript-2.16.1.jar
```

On Windows, use `py` or `python` and quote paths containing spaces.

Output: `dist/MMOSkriptBridge-1.1.0-beta.3.jar`.

The script removes and recreates only its local `build/classes` output directory, compiles the Java sources and packages them with `plugin.yml`.

No external downloads or server start happen automatically.
