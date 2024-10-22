package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;

public class FarmerGameEngineTest {
    private FarmerGameEngine engine;
    private static final river.Item BEANS = river.Item.ITEM_0;
    private static final river.Item GOOSE = river.Item.ITEM_1;
    private static final river.Item WOLF = river.Item.ITEM_2;
    private static final river.Item FARMER = river.Item.ITEM_3;

    @Before
    public void setUp() throws Exception {
        engine = new FarmerGameEngine();
    }

    @Test
    public void testObjectCallThroughs() {
        // Test the engine methods for each game object using the call-through methods

        // Test Farmer (Item.ITEM_3)
        Assert.assertEquals("F", engine.getItemLabel(Item.ITEM_3));  // Farmer label
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_3));  // Farmer starts at Location.START
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(Item.ITEM_3));  // Farmer's color is MAGENTA

        // Test Wolf (Item.ITEM_2)
        Assert.assertEquals("W", engine.getItemLabel(Item.ITEM_2));  // Wolf label
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));  // Wolf starts at Location.START
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_2));  // Wolf's color is CYAN

        // Test Goose (Item.ITEM_1)
        Assert.assertEquals("G", engine.getItemLabel(Item.ITEM_1));  // Goose label
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));  // Goose starts at Location.START
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_1));  // Goose's color is CYAN

        // Test Beans (Item.ITEM_0)
        Assert.assertEquals("B", engine.getItemLabel(Item.ITEM_0));  // Beans label
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_0));  // Beans start at Location.START
        Assert.assertEquals(Color.CYAN, engine.getItemColor(Item.ITEM_0));  // Beans' color is CYAN
    }

    // Helper method to transport an item across the river
    private void transport(Item item) {
        engine.loadBoat(item);
        engine.loadBoat(Item.ITEM_3);  // Always transport the farmer along with the item
        engine.rowBoat();
        engine.unloadBoat(item);
        engine.unloadBoat(Item.ITEM_3);  // Farmer disembarks after unloading the item
    }

    @Test
    public void testMidTransport() {
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_1));

        // Transport the goose across the river
        transport(Item.ITEM_1);

        // Verify that the goose is now on the other side
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(Item.ITEM_1));
    }

    @Test
    public void testWinningGame() {
        // Step 1: Transport the goose to the other side
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Step 2: Return alone
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Step 3: Transport the wolf to the other side
        transport(Item.ITEM_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Step 4: Return with the goose
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Step 5: Transport the beans to the other side
        transport(Item.ITEM_0);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Step 6: Return and transport the goose to the other side again
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        transport(Item.ITEM_1);

        // The game should be won now
        System.out.println("Final state:");
        System.out.println("Farmer: " + engine.getItemLocation(Item.ITEM_3));
        System.out.println("Goose: " + engine.getItemLocation(Item.ITEM_1));
        System.out.println("Wolf: " + engine.getItemLocation(Item.ITEM_2));
        System.out.println("Beans: " + engine.getItemLocation(Item.ITEM_0));
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());


    }


    @Test
    public void testLosingGame() {
        // Transport the goose to the other side
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Go back alone
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Transport the wolf to the other side
        transport(Item.ITEM_2);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Go back alone
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);

        // The game should be lost now
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {
        // Transport the goose
        transport(Item.ITEM_1);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Save the state
        Location wolfLoc = engine.getItemLocation(Item.ITEM_2);
        Location gooseLoc = engine.getItemLocation(Item.ITEM_1);
        Location beansLoc = engine.getItemLocation(Item.ITEM_0);
        Location farmerLoc = engine.getItemLocation(Item.ITEM_3);

        // This action should do nothing since the wolf is not on the same shore as the boat
        engine.loadBoat(Item.ITEM_2);

        // Check that the state has not changed
        Assert.assertEquals(wolfLoc, engine.getItemLocation(Item.ITEM_2));
        Assert.assertEquals(gooseLoc, engine.getItemLocation(Item.ITEM_1));
        Assert.assertEquals(beansLoc, engine.getItemLocation(Item.ITEM_0));
        Assert.assertEquals(farmerLoc, engine.getItemLocation(Item.ITEM_3));
    }

    @Test
    public void testFullBoat() {
        // Load two items (Farmer and Goose) into the boat
        engine.loadBoat(Item.ITEM_3);  // Farmer
        engine.loadBoat(Item.ITEM_1);  // Goose

        // Attempt to load a third item (Wolf) when the boat is full
        engine.loadBoat(Item.ITEM_2);  // Wolf

        // The Wolf should not be in the boat (its location should still be on the shore)
        Assert.assertEquals(Location.START, engine.getItemLocation(Item.ITEM_2));

        // Farmer and Goose should still be in the boat
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.ITEM_3));
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.ITEM_1));
    }

    @Test
    public void testRowWithoutFarmer() {
        // Load the Goose into the boat without the Farmer
        engine.loadBoat(Item.ITEM_1);

        // Try to row the boat without the Farmer
        engine.rowBoat();

        // The boat should not have moved (Goose should still be at START)
        Assert.assertEquals(Location.BOAT, engine.getItemLocation(Item.ITEM_1));
    }

    @Test
    public void testRepeatedBoatRowing() {
        // Load the Farmer into the boat
        engine.loadBoat(Item.ITEM_3);

        // Row the boat to the other side (FINISH)
        engine.rowBoat();
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());

        // Row the boat back to the start (START)
        engine.rowBoat();
        Assert.assertEquals(Location.START, engine.getBoatLocation());
    }

    @Test
    public void testWinCondition() {
        // Transport all items to the other side
        transport(Item.ITEM_1);  // Goose
        engine.loadBoat(Item.ITEM_3);  // Farmer returns alone
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        transport(Item.ITEM_2);  // Wolf
        engine.loadBoat(Item.ITEM_3);  // Farmer returns alone
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        transport(Item.ITEM_0);  // Beans
        engine.loadBoat(Item.ITEM_3);  // Farmer returns alone
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);
        transport(Item.ITEM_1);  // Goose

        // The game should now be won
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testMoveBeansFirst() {
        // Transport the Beans across the river
        transport(Item.ITEM_0);

        // Return the Farmer alone
        engine.loadBoat(Item.ITEM_3);
        engine.rowBoat();
        engine.unloadBoat(Item.ITEM_3);

        // Verify that the game hasn't been lost yet
        Assert.assertFalse(engine.gameIsLost());
    }


}
