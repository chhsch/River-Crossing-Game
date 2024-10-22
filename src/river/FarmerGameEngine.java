package river;

import java.awt.*;
import java.util.EnumMap;

public class FarmerGameEngine extends AbstractGameEngine {
    // Seats for the boat

    private static final Item BEANS = Item.ITEM_0;
    private static final Item GOOSE = Item.ITEM_1;
    private static final Item WOLF = Item.ITEM_2;
    private static final Item FARMER = Item.ITEM_3;


    public FarmerGameEngine() {

        itemMap = new EnumMap<>(Item.class);

        // Initialize game objects using Item enums
        // Initialize the game objects with labels and colors for the GUI
        itemMap.put(Item.ITEM_2, new GameObject("W", Location.START, Color.CYAN, false));  // W for Wolf
        itemMap.put(Item.ITEM_1, new GameObject("G", Location.START, Color.CYAN, false));  // G for Goose
        itemMap.put(Item.ITEM_0, new GameObject("B", Location.START, Color.CYAN, false));  // B for Beans
        itemMap.put(Item.ITEM_3, new GameObject("F", Location.START, Color.MAGENTA, true)); // F for Farmer

    }


    @Override
    public void loadBoat(Item id) {
        GameObject object = itemMap.get(id);

        // If the item is already in the boat or not at the boat's current location, do nothing
        if (object.getLocation() == Location.BOAT || object.getLocation() != boatLocation) {
            return;
        }

        // Count how many items are already in the boat (either seat1 or seat2)
        long itemsInBoat = (seat1 != null ? 1 : 0) + (seat2 != null ? 1 : 0);

        // Check if there's room on the boat (at most 2 items can be loaded)
        if (itemsInBoat >= 2) {
            return; // Boat is full
        }

        // Handle the Farmer (Item.ITEM_3) - Farmer always occupies seat1
        if (id == Item.ITEM_3) {
            if (seat1 == null) {

                seat1 = id;
                object.setLocation(Location.BOAT);
                return;
            }
            return;

        }

        // Handle non-farmer items - prioritize seat2 first, then seat1 if seat2 is taken
        if (seat2 == null) {
            seat2 = id;
            object.setLocation(Location.BOAT);  // Place item in seat2


        } else if (seat1 == null) {
            seat1 = id;
            object.setLocation(Location.BOAT);  // Place item in seat1


        }
    }


    @Override
    public void rowBoat() {

        if (getSeat1() != null && getItemIsDriver(getSeat1())) {
            super.rowBoat();  // Only row if seat1 has a driver (Farmer)
        }


    }


    @Override
    public boolean gameIsLost() {
        Location gooseLocation = getItemLocation(Item.ITEM_1); // Goose
        Location wolfLocation = getItemLocation(Item.ITEM_2);  // Wolf
        Location beansLocation = getItemLocation(Item.ITEM_0); // Beans
        Location farmerLocation = getItemLocation(Item.ITEM_3); // Farmer

        if (gooseLocation == Location.BOAT || gooseLocation == farmerLocation || gooseLocation == boatLocation) {
            return false;
        }
        if (gooseLocation == wolfLocation && gooseLocation != farmerLocation) {
            return true; // Goose with wolf without farmer
        }
        if (gooseLocation == beansLocation && gooseLocation != farmerLocation) {
            return true; // Goose with beans without farmer
        }
        return false;
    }


    @Override
    public int numberOfItems() {
        return 4;  // Example: 4 items in the Farmer Game (farmer, goose, wolf, beans)
    }

}
