package todfresser.smash.extrafunctions;

import org.bukkit.Material;

import static org.bukkit.Material.*;

public class MaterialUtils {

    public static boolean equals(Material material, Material... others) {
        for (Material m : others) {
            if (m == material) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsSign(Material material) {
        return equals(material, SPRUCE_SIGN, SPRUCE_WALL_SIGN, ACACIA_SIGN, OAK_SIGN, OAK_WALL_SIGN, ACACIA_WALL_SIGN,
                BIRCH_SIGN, BIRCH_WALL_SIGN, CRIMSON_SIGN, CRIMSON_WALL_SIGN, DARK_OAK_SIGN, DARK_OAK_WALL_SIGN,
                JUNGLE_SIGN, JUNGLE_WALL_SIGN, WARPED_SIGN, WARPED_WALL_SIGN);
    }

    public static boolean equalsClickableBlock(Material material) {
        return equals(material, ACACIA_DOOR, BIRCH_DOOR, DARK_OAK_DOOR, JUNGLE_DOOR, SPRUCE_DOOR, CRIMSON_DOOR,
                IRON_DOOR, OAK_DOOR, WARPED_DOOR, ACACIA_FENCE_GATE, BIRCH_FENCE_GATE, DARK_OAK_FENCE_GATE,
                JUNGLE_FENCE_GATE, SPRUCE_FENCE_GATE,
                CRIMSON_FENCE_GATE, OAK_FENCE_GATE);
    }

    public static boolean equalsNonClickableBlock(Material material) {
        return equals(material, ANVIL, CRAFTING_TABLE, ENCHANTING_TABLE, CARTOGRAPHY_TABLE, FLETCHING_TABLE,
                SMITHING_TABLE, FURNACE, FURNACE_MINECART, BLAST_FURNACE);
    }

}
