package ru.akrien.mythicskriptbridge.syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import ru.akrien.skriptbridge.AkrienSkriptBridge;

public final class EffCastMythicSkill
extends Effect {
    private int pattern;
    private Expression<String> skill;
    private Expression<Entity> caster;
    private Expression<Entity> trigger;
    private Expression<Entity> entityTarget;
    private Expression<Location> locationTarget;
    private Expression<Number> delay;
    private Expression<Number> repeat;

    public boolean init(Expression<?>[] expressionArray, int n, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.pattern = n;
        if (n == 0) {
            this.skill = (Expression<String>) expressionArray[0];
            this.caster = (Expression<Entity>) expressionArray[1];
            this.trigger = (Expression<Entity>) expressionArray[2];
            this.entityTarget = (Expression<Entity>) expressionArray[3];
        } else if (n == 1) {
            this.skill = (Expression<String>) expressionArray[0];
            this.caster = (Expression<Entity>) expressionArray[1];
            this.trigger = (Expression<Entity>) expressionArray[2];
            this.locationTarget = (Expression<Location>) expressionArray[3];
        } else if (n == 2) {
            this.caster = (Expression<Entity>) expressionArray[0];
            this.skill = (Expression<String>) expressionArray[1];
            this.trigger = (Expression<Entity>) expressionArray[2];
            this.entityTarget = (Expression<Entity>) expressionArray[3];
            this.delay = (Expression<Number>) expressionArray[4];
            this.repeat = (Expression<Number>) expressionArray[5];
        } else {
            this.caster = (Expression<Entity>) expressionArray[0];
            this.skill = (Expression<String>) expressionArray[1];
            this.trigger = (Expression<Entity>) expressionArray[2];
            this.locationTarget = (Expression<Location>) expressionArray[3];
            this.delay = (Expression<Number>) expressionArray[4];
            this.repeat = (Expression<Number>) expressionArray[5];
        }
        return true;
    }

    protected void execute(Event event) {
        Entity entity;
        Entity entity2 = (Entity)this.caster.getSingle(event);
        String string = (String)this.skill.getSingle(event);
        Entity entity3 = entity = this.trigger == null ? entity2 : (Entity)this.trigger.getSingle(event);
        if (entity2 == null || string == null) {
            return;
        }
        Entity entity4 = this.entityTarget == null ? null : (Entity)this.entityTarget.getSingle(event);
        Location location = this.locationTarget == null ? null : (Location)this.locationTarget.getSingle(event);
        long l = EffCastMythicSkill.value(this.delay, event);
        int n = (int)Math.max(0L, EffCastMythicSkill.value(this.repeat, event));
        Runnable runnable = () -> {
            if (entity4 != null) {
                AkrienSkriptBridge.instance().mythic().castAtEntity(entity2, string, entity, entity4);
            } else if (location != null) {
                AkrienSkriptBridge.instance().mythic().castAtLocation(entity2, string, entity, location);
            }
        };
        if (l <= 0L && n <= 0) {
            runnable.run();
            return;
        }
        EffCastMythicSkill.scheduleFinite(runnable, Math.max(0L, l), Math.max(1L, l), n + 1);
    }

    private static void scheduleFinite(Runnable runnable, long l, long l2, int n) {
        if (n <= 0) {
            return;
        }
        Bukkit.getScheduler().runTaskLater((JavaPlugin)AkrienSkriptBridge.instance(), () -> {
            runnable.run();
            EffCastMythicSkill.scheduleFinite(runnable, l2, l2, n - 1);
        }, l);
    }

    private static long value(Expression<Number> expression, Event event) {
        Number number = expression == null ? (Number)null : (Number)((Number)expression.getSingle(event));
        return number == null ? 0L : Math.max(0L, number.longValue());
    }

    public String toString(Event event, boolean bl) {
        return "cast MythicMobs skill";
    }
}

