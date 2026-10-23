package ru.akrien.mmoitemsskript.syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class CondMMOItemExists
extends Condition {
    private Expression<String> type;
    private Expression<String> id;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.type = (Expression<String>) expressionArray[0];
        this.id = (Expression<String>) expressionArray[1];
        this.setNegated(n == 1);
        return true;
    }

    public boolean check(Event event) {
        String string = (String)this.type.getSingle(event);
        String string2 = (String)this.id.getSingle(event);
        boolean bl = string != null && string2 != null && AkrienSkriptBridge.instance().items().generate(string, string2) != null;
        return bl ^ this.isNegated();
    }

    public String toString(Event event, boolean bl) {
        return "MMOItems template exists";
    }
}

