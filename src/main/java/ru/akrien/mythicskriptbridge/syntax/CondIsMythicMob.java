package ru.akrien.mythicskriptbridge.syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class CondIsMythicMob
extends Condition {
    private Expression<Entity> entity;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>) expressionArray[0];
        this.setNegated(n == 1);
        return true;
    }

    public boolean check(Event event) {
        boolean bl = AkrienSkriptBridge.instance().mythic().isMythicMob((Entity)this.entity.getSingle(event));
        return bl ^ this.isNegated();
    }

    public String toString(Event event, boolean bl) {
        return "mythicmob check";
    }
}

