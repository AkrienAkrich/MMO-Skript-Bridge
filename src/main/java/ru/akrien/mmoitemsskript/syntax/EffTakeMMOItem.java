package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class EffTakeMMOItem
extends Effect {
    private Expression<Number> amount;
    private Expression<String> type;
    private Expression<String> id;
    private Expression<Player> players;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.amount = (Expression<Number>) expressionArray[0];
        this.type = (Expression<String>) expressionArray[1];
        this.id = (Expression<String>) expressionArray[2];
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
        for (Player player : (Player[])this.players.getArray(event)) {
            int n2 = n;
            ItemStack[] itemStackArray = player.getInventory().getContents();
            for (int i = 0; i < itemStackArray.length && n2 > 0; ++i) {
                ItemStack itemStack = itemStackArray[i];
                if (!AkrienSkriptBridge.instance().items().matches(itemStack, string, string2)) continue;
                int n3 = Math.min(n2, itemStack.getAmount());
                if (n3 >= itemStack.getAmount()) {
                    player.getInventory().setItem(i, null);
                } else {
                    itemStack.setAmount(itemStack.getAmount() - n3);
                    player.getInventory().setItem(i, itemStack);
                }
                n2 -= n3;
            }
        }
    }

    public String toString(Event event, boolean bl) {
        return "take MMOItem";
    }
}

