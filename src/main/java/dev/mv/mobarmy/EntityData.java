package dev.mv.mobarmy;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;

public class EntityData {

    public EntityType type;
    public EntityEquipment equipment;

    public EntityData(LivingEntity entity) {
        type = entity.getType();
        equipment = entity.getEquipment();
    }

    public void spawn(Location location) {
        LivingEntity e = (LivingEntity) location.getWorld().spawnEntity(location, type);
        e.getEquipment().clear();
        e.getEquipment().setItem(EquipmentSlot.HEAD, equipment.getItem(EquipmentSlot.HEAD));
        e.getEquipment().setItem(EquipmentSlot.CHEST, equipment.getItem(EquipmentSlot.CHEST));
        e.getEquipment().setItem(EquipmentSlot.LEGS, equipment.getItem(EquipmentSlot.LEGS));
        e.getEquipment().setItem(EquipmentSlot.FEET, equipment.getItem(EquipmentSlot.FEET));
        e.getEquipment().setItem(EquipmentSlot.HAND, equipment.getItem(EquipmentSlot.HAND));
        e.getEquipment().setItem(EquipmentSlot.OFF_HAND, equipment.getItem(EquipmentSlot.OFF_HAND));
    }

}
