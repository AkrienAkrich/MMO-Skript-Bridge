package ru.akrien.skriptbridge;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.akrien.mmoitemsskript.MMOItemsBridge;
import ru.akrien.mmoitemsskript.syntax.*;
import ru.akrien.mythicskriptbridge.MythicBridge;
import ru.akrien.mythicskriptbridge.syntax.*;
public final class AkrienSkriptBridge extends JavaPlugin {
 private static AkrienSkriptBridge instance;
 private MMOItemsBridge items;
 private MythicBridge mythic;
 public static AkrienSkriptBridge instance() { return instance; }
 public MMOItemsBridge items() { return items; }
 public MythicBridge mythic() { return mythic; }
 @Override public void onEnable() {
  if (getServer().getPluginManager().getPlugin("AkrienSkriptBridge") != null || getServer().getPluginManager().getPlugin("MMOItems-Skript") != null || getServer().getPluginManager().getPlugin("MythicMobs-Skript-Bridge") != null) {
   getLogger().severe("Remove AkrienSkriptBridge and the original standalone bridges before installing MMOSkriptBridge.");
   getServer().getPluginManager().disablePlugin(this); return;
  }
  instance = this;
  if (getServer().getPluginManager().isPluginEnabled("MMOItems")) {
   try { items = new MMOItemsBridge(); registerItems(); }
   catch (ReflectiveOperationException | LinkageError ex) { items = null; getLogger().log(java.util.logging.Level.SEVERE, "MMOItems integration failed", ex); }
  }
  if (getServer().getPluginManager().isPluginEnabled("MythicMobs")) {
   try { mythic = new MythicBridge(); registerMythic(); }
   catch (ReflectiveOperationException | LinkageError ex) { mythic = null; getLogger().log(java.util.logging.Level.SEVERE, "MythicMobs integration failed", ex); }
  }
  printBanner();
 }
 private static String color(String hex) {
  StringBuilder result = new StringBuilder("\u00a7x");
  for (char digit : hex.toCharArray()) result.append('\u00a7').append(digit);
  return result.toString();
 }
 private void line(String text) {
  getServer().getConsoleSender().sendMessage(text + "\u00a7r");
 }
 private String status(String name, boolean ready, String success) {
  org.bukkit.plugin.Plugin dependency = getServer().getPluginManager().getPlugin(name);
  if (dependency == null) return "\u00a78" + "- Not installed";
  if (!ready) return "\u00a7c" + "! Failed";
  return "\u00a7a" + "+ " + success + "\u00a78" + "  " + dependency.getDescription().getVersion();
 }
 private void printBanner() {
  String dim = "\u00a78", white = "\u00a7f", violet = "\u00a7d", purple = "\u00a75";
  line("");
  line(purple + "  █   █  ████  ████   " + white + "\u00a7lMMOSkriptBridge");
  line(purple + "  ██ ██  █     █   █  " + dim + "by " + violet + "AkrienDiverse");
  line(violet + "  █ █ █  ████  ████   " + dim + "────────────────────────────");
  line(violet + "  █   █     █  █   █  " + white + getDescription().getVersion() + dim + "  /  " + white + getServer().getName());
  line(white + "  █   █  ████  ████   " + dim + getServer().getBukkitVersion());
  line("");
  line(dim + "  MMOItems   " + status("MMOItems", items != null, "Connected"));
  line(dim + "  MythicMobs " + status("MythicMobs", mythic != null, "Connected"));
  line(dim + "  Skript     " + status("Skript", getServer().getPluginManager().isPluginEnabled("Skript"), "Ready"));
  line("");
 }
 private void registerItems() {
        Skript.registerCondition(CondIsMMOItem.class, (String[])new String[]{"%itemstack% is [a] mmoitem", "%itemstack% is not [a] mmoitem", "%itemstack% is [a] mmoitem %string% %string%", "%itemstack% is not [a] mmoitem %string% %string%"});
        Skript.registerCondition(CondMMOItemExists.class, (String[])new String[]{"mmoitem %string% %string% exists", "mmoitem %string% %string% does not exist"});
        Skript.registerExpression(ExprMMOItemId.class, String.class, (ExpressionType)ExpressionType.SIMPLE, (String[])new String[]{"[the] mmoitem id of %itemstack%"});
        Skript.registerExpression(ExprMMOItemType.class, String.class, (ExpressionType)ExpressionType.SIMPLE, (String[])new String[]{"[the] mmoitem type of %itemstack%"});
        Skript.registerExpression(ExprMMOItem.class, ItemStack.class, (ExpressionType)ExpressionType.SIMPLE, (String[])new String[]{"[a] mmoitem %string% %string%"});
        Skript.registerExpression(ExprMMOItemAmount.class, Number.class, (ExpressionType)ExpressionType.SIMPLE, (String[])new String[]{"[the] amount of mmoitem %string% %string% (in|of) %player%['s inventory]"});
        Skript.registerEffect(EffGiveMMOItem.class, (String[])new String[]{"give mmoitem %string% %string% [with amount %-number%] to %players%"});
        Skript.registerEffect(EffTakeMMOItem.class, (String[])new String[]{"take [amount %-number%] mmoitem %string% %string% from %players%"});
 }
 private void registerMythic() {
        Skript.registerEffect(EffCastMythicSkill.class, (String[])new String[]{"cast mythic skill %string% from %entity% [with trigger %-entity%] at %entity%", "cast mythic skill %string% from %entity% [with trigger %-entity%] at %location%", "(make|let) player %entity% cast skill %string% with trigger %entity% at entity %entity% [with delay %-number%] [and repeat %-number%]", "(make|let) player %entity% cast skill %string% with trigger %entity% at location %location% [with delay %-number%] [and repeat %-number%]"});
        Skript.registerCondition(CondIsMythicMob.class, (String[])new String[]{"%entity% is [a] mythicmob", "%entity% is not [a] mythicmob"});
        Skript.registerCondition(CondMythicSkillExists.class, (String[])new String[]{"mythic skill %string% exists", "mythic skill %string% does not exist"});
        Skript.registerExpression(ExprMythicMobId.class, String.class, (ExpressionType)ExpressionType.SIMPLE, (String[])new String[]{"[the] mythic mob id of %entity%", "[the] mythicmob id of %entity%"});
 }
 @Override public void onDisable() {
  getServer().getScheduler().cancelTasks(this);
  items = null; mythic = null; instance = null;
 }
}
