package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class ExprMMOItemAmount
extends SimpleExpression<Number> {
    private Expression<String> type;
    private Expression<String> id;
    private Expression<Player> player;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.type = (Expression<String>) expressionArray[0];
        this.id = (Expression<String>) expressionArray[1];
        this.player = (Expression<Player>) expressionArray[2];
        return true;
    }

    protected Number[] get(Event event) {
        String string = (String)this.type.getSingle(event);
        String string2 = (String)this.id.getSingle(event);
        Player player = (Player)this.player.getSingle(event);
        if (string == null || string2 == null || player == null) {
            return null;
        }
        int n = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (!AkrienSkriptBridge.instance().items().matches(itemStack, string, string2)) continue;
            n += itemStack.getAmount();
        }
        return new Number[]{n};
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    public String toString(Event event, boolean bl) {
        return "amount of MMOItem";
    }
}

