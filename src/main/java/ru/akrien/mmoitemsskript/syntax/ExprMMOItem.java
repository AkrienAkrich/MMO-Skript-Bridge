package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class ExprMMOItem
extends SimpleExpression<ItemStack> {
    private Expression<String> type;
    private Expression<String> id;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.type = (Expression<String>) expressionArray[0];
        this.id = (Expression<String>) expressionArray[1];
        return true;
    }

    protected ItemStack[] get(Event event) {
        ItemStack[] itemStackArray;
        ItemStack itemStack;
        String string = (String)this.type.getSingle(event);
        String string2 = (String)this.id.getSingle(event);
        ItemStack itemStack2 = itemStack = string == null || string2 == null ? null : AkrienSkriptBridge.instance().items().generate(string, string2);
        if (itemStack == null) {
            itemStackArray = null;
        } else {
            ItemStack[] itemStackArray2 = new ItemStack[1];
            itemStackArray = itemStackArray2;
            itemStackArray2[0] = itemStack;
        }
        return itemStackArray;
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    public String toString(Event event, boolean bl) {
        return "generated MMOItem";
    }
}

