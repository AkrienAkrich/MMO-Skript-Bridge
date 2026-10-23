package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class CondIsMMOItem
extends Condition {
    private Expression<ItemStack> item;
    private Expression<String> type;
    private Expression<String> id;
    private boolean specific;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.item = (Expression<ItemStack>) expressionArray[0];
        this.specific = n >= 2;
        this.setNegated(n == 1 || n == 3);
        if (this.specific) {
            this.type = (Expression<String>) expressionArray[1];
            this.id = (Expression<String>) expressionArray[2];
        }
        return true;
    }

    public boolean check(Event event) {
        boolean bl;
        ItemStack itemStack = (ItemStack)this.item.getSingle(event);
        if (!this.specific) {
            bl = AkrienSkriptBridge.instance().items().isMMOItem(itemStack);
        } else {
            String string = (String)this.type.getSingle(event);
            String string2 = (String)this.id.getSingle(event);
            bl = string != null && string2 != null && AkrienSkriptBridge.instance().items().matches(itemStack, string, string2);
        }
        return bl ^ this.isNegated();
    }

    public String toString(Event event, boolean bl) {
        return "MMOItems item check";
    }
}

