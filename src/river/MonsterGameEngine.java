package river;

import java.awt.*;
import java.util.EnumMap;

public class MonsterGameEngine extends AbstractGameEngine {

    private static final Item MONSTER_1 = Item.ITEM_0;
    private static final Item MUNCHKIN_1 = Item.ITEM_1;
    private static final Item MONSTER_2 = Item.ITEM_2;
    private static final Item MUNCHKIN_2 = Item.ITEM_3;
    private static final Item MONSTER_3 = Item.ITEM_4;
    private static final Item MUNCHKIN_3 = Item.ITEM_5;


    public MonsterGameEngine() {

        itemMap = new EnumMap<>(Item.class);

        // Initialize game objects using Item enums
        // Initialize the game objects with labels and colors for the GUI
        itemMap.put(MONSTER_1, new GameObject("M1", Location.START, Color.RED, true));  // Monster 1
        itemMap.put(MUNCHKIN_1, new GameObject("Mu1", Location.START, Color.GREEN, true));  // Munchkin 1
        itemMap.put(MONSTER_2, new GameObject("M2", Location.START, Color.RED, true));  // Monster 2
        itemMap.put(MUNCHKIN_2, new GameObject("Mu2", Location.START, Color.GREEN, true));  // Munchkin 2
        itemMap.put(MONSTER_3, new GameObject("M3", Location.START, Color.RED, true));  // Monster 3
        itemMap.put(MUNCHKIN_3, new GameObject("Mu3", Location.START, Color.GREEN, true));  // Munchkin 3

    }

    private boolean isOutnumbered(Location shore) {
        int munchkinCount = 0;
        int monsterCount = 0;

        for (Item item : itemMap.keySet()) {
            if (getItemLocation(item) == shore) {
                if (item == MUNCHKIN_1 || item == MUNCHKIN_2 || item == MUNCHKIN_3) {
                    munchkinCount++;
                } else if (item == MONSTER_1 || item == MONSTER_2 || item == MONSTER_3) {
                    monsterCount++;
                }
            }
        }

        if (getBoatLocation() == shore) {
            if (seat1 != null) {
                if (seat1 == MUNCHKIN_1 || seat1 == MUNCHKIN_2 || seat1 == MUNCHKIN_3) {
                    munchkinCount++;
                } else if (seat1 == MONSTER_1 || seat1 == MONSTER_2 || seat1 == MONSTER_3) {
                    monsterCount++;
                }
            }
            if (seat2 != null) {
                if (seat2 == MUNCHKIN_1 || seat2 == MUNCHKIN_2 || seat2 == MUNCHKIN_3) {
                    munchkinCount++;
                } else if (seat2 == MONSTER_1 || seat2 == MONSTER_2 || seat2 == MONSTER_3) {
                    monsterCount++;
                }
            }
        }

        return munchkinCount > 0 && monsterCount > munchkinCount;
    }


    @Override
    public void rowBoat() {

        if (getItemIsDriver(getSeat1()) || getItemIsDriver(getSeat2()))
            super.rowBoat();
    }


    @Override
    public boolean gameIsLost() {
        // Check if munchkins are outnumbered on either START or FINISH side
        boolean startOutnumbered = isOutnumbered(Location.START);
        boolean finishOutnumbered = isOutnumbered(Location.FINISH);

        // If munchkins are outnumbered on either side, the game is lost
        return startOutnumbered || finishOutnumbered;
    }

    @Override
    public int numberOfItems() {
        return 6;
    }

}

