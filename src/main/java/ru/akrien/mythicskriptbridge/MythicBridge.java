package ru.akrien.mythicskriptbridge;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public final class MythicBridge {
    private final Object mythic;
    private final Object helper;
    private final Method castFull;
    private final Method isMythicMob;
    private final Method getMythicMobInstance;
    private final Method getSkillManager;

    public MythicBridge() throws ReflectiveOperationException {
        Class<?> clazz = Class.forName("io.lumine.mythic.bukkit.MythicBukkit");
        this.mythic = clazz.getMethod("inst", new Class[0]).invoke(null, new Object[0]);
        this.helper = this.mythic.getClass().getMethod("getAPIHelper", new Class[0]).invoke(this.mythic, new Object[0]);
        this.castFull = this.helper.getClass().getMethod("castSkill", Entity.class, String.class, Entity.class, Location.class, Collection.class, Collection.class, Float.TYPE);
        this.isMythicMob = this.helper.getClass().getMethod("isMythicMob", Entity.class);
        this.getMythicMobInstance = this.helper.getClass().getMethod("getMythicMobInstance", Entity.class);
        this.getSkillManager = this.mythic.getClass().getMethod("getSkillManager", new Class[0]);
    }

    public boolean castAtEntity(Entity entity, String string, Entity entity2, Entity entity3) {
        if (entity == null || string == null || entity3 == null) {
            return false;
        }
        if (entity2 == null) {
            entity2 = entity;
        }
        try {
            Boolean bl;
            Object object = this.castFull.invoke(this.helper, entity, string, entity2, entity.getLocation(), Collections.singletonList(entity3), Collections.emptyList(), Float.valueOf(1.0f));
            return object instanceof Boolean && (bl = (Boolean)object) != false;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return false;
        }
    }

    public boolean castAtLocation(Entity entity, String string, Entity entity2, Location location) {
        if (entity == null || string == null || location == null) {
            return false;
        }
        if (entity2 == null) {
            entity2 = entity;
        }
        try {
            Boolean bl;
            Object object = this.castFull.invoke(this.helper, entity, string, entity2, entity.getLocation(), Collections.emptyList(), Collections.singletonList(location), Float.valueOf(1.0f));
            return object instanceof Boolean && (bl = (Boolean)object) != false;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return false;
        }
    }

    public boolean isMythicMob(Entity entity) {
        if (entity == null) {
            return false;
        }
        try {
            Boolean bl;
            Object object = this.isMythicMob.invoke(this.helper, entity);
            return object instanceof Boolean && (bl = (Boolean)object) != false;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return false;
        }
    }

    public String getMobId(Entity entity) {
        if (entity == null) {
            return null;
        }
        try {
            Object object = this.getMythicMobInstance.invoke(this.helper, entity);
            if (object == null) {
                return null;
            }
            Object object2 = object.getClass().getMethod("getType", new Class[0]).invoke(object, new Object[0]);
            if (object2 == null) {
                return null;
            }
            Object object3 = object2.getClass().getMethod("getInternalName", new Class[0]).invoke(object2, new Object[0]);
            return object3 == null ? null : String.valueOf(object3);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    public boolean skillExists(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        try {
            Object object = this.getSkillManager.invoke(this.mythic, new Object[0]);
            Object object2 = object.getClass().getMethod("getSkill", String.class).invoke(object, string);
            if (object2 instanceof Optional) {
                Optional optional = (Optional)object2;
                return optional.isPresent();
            }
            return object2 != null;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return false;
        }
    }
}

