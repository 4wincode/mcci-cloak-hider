package in.fourw.mccicloakhider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class CloakFilter {
    private static final String ARMOR_STAND_ID = "minecraft:armor_stand";
    private static final String CLOAK_MODEL_PREFIX = "mcc:island_cosmetics/general/back/";
    private static final String MCC_DOMAIN = "mccisland.net";

    private CloakFilter() {}

    public static boolean shouldHide(Entity entity) {
        if (entity.getVehicle() == null) {
            return false;
        }

        Minecraft mc = Minecraft.getInstance();
        if (!mc.options.getCameraType().isFirstPerson()) {
            return false;
        }

        Entity camera = mc.getCameraEntity();
        if (camera == null || entity.getRootVehicle() != camera) {
            return false;
        }

        if (!(entity instanceof LivingEntity living)) {
            return false;
        }

        if (!ARMOR_STAND_ID.equals(String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType())))) {
            return false;
        }

        ServerData server = mc.getCurrentServer();
        if (server == null || !server.ip.toLowerCase().contains(MCC_DOMAIN)) {
            return false;
        }

        ItemStack head = living.getItemBySlot(EquipmentSlot.HEAD);
        if (head.isEmpty()) {
            return false;
        }

        var model = head.get(DataComponents.ITEM_MODEL);
        return model != null && model.toString().startsWith(CLOAK_MODEL_PREFIX);
    }
}
