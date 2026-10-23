package ru.akrien.mmoitemsskript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import org.bukkit.inventory.ItemStack;

public final class MMOItemsBridge {
    private final Class<?> mmoItemsClass = Class.forName("net.Indyuce.mmoitems.MMOItems");
    private final Class<?> typeClass = Class.forName("net.Indyuce.mmoitems.api.Type");
    private final Method getIdMethod = this.mmoItemsClass.getMethod("getID", ItemStack.class);
    private final Method getTypeNameMethod = this.mmoItemsClass.getMethod("getTypeName", ItemStack.class);
    private final Field pluginField = this.mmoItemsClass.getField("plugin");

    public MMOItemsBridge() throws ReflectiveOperationException {}

    public boolean isMMOItem(ItemStack itemStack) {
        return itemStack != null && this.getId(itemStack) != null && this.getType(itemStack) != null;
    }

    public String getId(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        try {
            Object object = this.getIdMethod.invoke(null, itemStack);
            if (object == null) {
                return null;
            }
            String string = String.valueOf(object);
            return string.isBlank() ? null : string;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    public String getType(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        try {
            Object object = this.getTypeNameMethod.invoke(null, itemStack);
            if (object == null) {
                return null;
            }
            String string = String.valueOf(object);
            return string.isBlank() ? null : string;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    public boolean matches(ItemStack itemStack, String string, String string2) {
        String string3 = this.getType(itemStack);
        String string4 = this.getId(itemStack);
        return string3 != null && string4 != null && string3.equalsIgnoreCase(MMOItemsBridge.normalize(string)) && string4.equalsIgnoreCase(MMOItemsBridge.normalize(string2));
    }

    public ItemStack generate(String string, String string2) {
        if (string == null || string2 == null) {
            return null;
        }
        try {
            ItemStack itemStack;
            Object object = this.pluginField.get(null);
            if (object == null) {
                return null;
            }
            Method method = object.getClass().getMethod("getTypes", new Class[0]);
            Object object2 = method.invoke(object, new Object[0]);
            Method method2 = object2.getClass().getMethod("get", String.class);
            Object object3 = method2.invoke(object2, MMOItemsBridge.normalize(string));
            if (object3 == null) {
                return null;
            }
            Method method3 = object.getClass().getMethod("getItem", this.typeClass, String.class);
            Object object4 = method3.invoke(object, object3, MMOItemsBridge.normalize(string2));
            return object4 instanceof ItemStack ? (itemStack = (ItemStack)object4) : null;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            return null;
        }
    }

    private static String normalize(String string) {
        return string == null ? "" : string.trim().toUpperCase(Locale.ROOT).replace('-', '_').replace(' ', '_');
    }
}

