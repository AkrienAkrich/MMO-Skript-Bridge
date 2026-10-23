package ru.akrien.mythicskriptbridge.syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class ExprMythicMobId
extends SimpleExpression<String> {
    private Expression<Entity> entity;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>) expressionArray[0];
        return true;
    }

    protected String[] get(Event event) {
        String[] stringArray;
        String string = AkrienSkriptBridge.instance().mythic().getMobId((Entity)this.entity.getSingle(event));
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
        return "mythic mob id";
    }
}

