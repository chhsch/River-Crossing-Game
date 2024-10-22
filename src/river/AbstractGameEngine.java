package river;

import java.awt.Color;
import java.util.*;

public abstract class AbstractGameEngine implements GameEngine {

    protected Map<Item, GameObject> itemMap = new EnumMap<>(Item.class);
    protected Location boatLocation = Location.START;
    protected Item seat1 = null;
    protected Item seat2 = null;

    // Abstract method for game-specific loss conditions
    public abstract boolean gameIsLost();

    // Abstract method to get the number of items in the game
    public abstract int numberOfItems();


    @Override

    public String getItemLabel(Item id) {
        return itemMap.get(id).getLable();
    }

    @Override

    public Color getItemColor(Item id) {
        return itemMap.get(id).getColor();
    }

    @Override

    public boolean getItemIsDriver(Item id) {
        // Check if the item is a driver (only the farmer can drive)
        return itemMap.get(id).isDriver();
    }

    @Override
    public Location getItemLocation(Item item) {
        return itemMap.get(item).getLocation();
    }


    @Override
    public void setItemLocation(Item item, Location location) {
        itemMap.get(item).setLocation(location);
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }


    @Override
    public void loadBoat(Item item) {
        GameObject object = itemMap.get(item);

        // If the item is already in the boat or not at the boat's location, do nothing
        if (object.getLocation() == Location.BOAT || object.getLocation() != boatLocation) {
            return;
        }

        // Count how many items are already in the boat (either seat1 or seat2)
        long itemsInBoat = (seat1 != null ? 1 : 0) + (seat2 != null ? 1 : 0);

        // Check if there's room on the boat (at most 2 items can be loaded)
        if (itemsInBoat >= 2) {
            return;  // Boat is full
        }

        // Load item into seat1 or seat2
        if (seat1 == null) {
            seat1 = item;
        } else if (seat2 == null) {
            seat2 = item;
        }

        object.setLocation(Location.BOAT);
    }


    @Override
    public void unloadBoat(Item item) {
        GameObject object = itemMap.get(item);

        // If the item is in the boat, unload it
        if (object.getLocation() == Location.BOAT) {
            object.setLocation(boatLocation);  // Place the item at the boat's location (START or FINISH)

            if (seat1 == item) {
                seat1 = null;
            } else if (seat2 == item) {
                seat2 = null;
            }
        }
    }


    @Override
    public void rowBoat() {

        boatLocation = (boatLocation == Location.START) ? Location.FINISH : Location.START;
    }


    @Override
    public boolean gameIsWon() {
        // The game is won when all items are at the FINISH location
        for (Item item : itemMap.keySet()) {
            if (itemMap.get(item).getLocation() != Location.FINISH) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void resetGame() {
        // Reset the boat to the starting location
        boatLocation = Location.START;
        seat1 = null;
        seat2 = null;

        // Reset all items to their starting location
        for (Item item : itemMap.keySet()) {
            itemMap.get(item).setLocation(Location.START);
        }
    }

    public Item getSeat1() {
        return seat1;
    }

    public Item getSeat2() {
        return seat2;
    }

    @Override
    public List<Item> getItems() {
        // Return a sorted list of items based on their ordinal values
        List<Item> itemList = new ArrayList<>(itemMap.keySet());
        itemList.sort((a, b) -> a.ordinal() - b.ordinal());  // Ensure items are sorted by ordinal value
        return itemList;
    }


}
