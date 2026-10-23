package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class EffGiveMMOItem
extends Effect {
    private Expression<String> type;
    private Expression<String> id;
    private Expression<Number> amount;
    private Expression<Player> players;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.type = (Expression<String>) expressionArray[0];
        this.id = (Expression<String>) expressionArray[1];
        this.amount = (Expression<Number>) expressionArray[2];
        this.players = (Expression<Player>) expressionArray[3];
        return true;
    }

    protected void execute(Event event) {
        String string = (String)this.type.getSingle(event);
        String string2 = (String)this.id.getSingle(event);
        if (string == null || string2 == null) {
            return;
        }
        Number number = this.amount == null ? (Number)null : (Number)((Number)this.amount.getSingle(event));
        int n = Math.max(1, number == null ? 1 : number.intValue());
        ItemStack itemStack = AkrienSkriptBridge.instance().items().generate(string, string2);
        if (itemStack == null) {
            Skript.warning((String)("Unknown MMOItem: " + string + " " + string2));
            return;
        }
        for (Player player : (Player[])this.players.getArray(event)) {
            int n2;
            for (int i = n; i > 0; i -= n2) {
                ItemStack itemStack2 = itemStack.clone();
                n2 = Math.min(i, Math.max(1, itemStack2.getMaxStackSize()));
                itemStack2.setAmount(n2);
                Map<Integer, ItemStack> map = player.getInventory().addItem(new ItemStack[]{itemStack2});
                for (ItemStack itemStack3 : map.values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack3);
                }
            }
        }
    }

    public String toString(Event event, boolean bl) {
        return "give MMOItem";
    }
}

