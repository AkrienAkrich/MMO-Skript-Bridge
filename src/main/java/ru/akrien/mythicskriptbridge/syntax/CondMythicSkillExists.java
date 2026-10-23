package ru.akrien.mythicskriptbridge.syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class CondMythicSkillExists
extends Condition {
    private Expression<String> skill;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.skill = (Expression<String>) expressionArray[0];
        this.setNegated(n == 1);
        return true;
    }

    public boolean check(Event event) {
        String string = (String)this.skill.getSingle(event);
        boolean bl = string != null && AkrienSkriptBridge.instance().mythic().skillExists(string);
        return bl ^ this.isNegated();
    }

    public String toString(Event event, boolean bl) {
        return "mythic skill exists";
    }
}

