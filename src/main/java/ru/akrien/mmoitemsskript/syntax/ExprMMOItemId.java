package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class ExprMMOItemId
extends SimpleExpression<String> {
    private Expression<ItemStack> item;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.item = (Expression<ItemStack>) expressionArray[0];
        return true;
    }

    protected String[] get(Event event) {
        String[] stringArray;
        String string = AkrienSkriptBridge.instance().items().getId((ItemStack)this.item.getSingle(event));
        if (string == null) {
            stringArray = null;
        } else {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = string;
        }
        return stringArray;
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public String toString(Event event, boolean bl) {
        return "MMOItems item id";
    }
}

