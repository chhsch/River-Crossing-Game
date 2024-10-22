package river;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;  // Make sure to import java.util.List


/**
 * Graphical interface for the River application
 *
 * @author Chih-Hsing Chen
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Fields (hotspots)
    // ==========================================================
    // Base coordinates for left shore, right shore, and boat
    private final int leftBaseX = 20;
    private final int leftBaseY = 275;
    private final int rightBaseX = 670;
    private final int rightBaseY = 275;
    private int boatX = 140;
    private int boatY = 275;
    private Rectangle boatRectangle = new Rectangle(boatX, boatY, 110, 50);

    // Offsets for items on the shore
    private final int[] dx = {0, 60, 0, 60, 0, 60};  // X-offsets for items 0 through 5
    private final int[] dy = {0, 0, -60, -60, -120, -120};  // Y-offsets for items 0 through 5


    private final Rectangle restartFarmerButtonRect = new Rectangle(200, 120, 100, 30);
    private final Rectangle restartMonsterButtonRect = new Rectangle(400, 120, 100, 30);

    // Model
//    private FarmerGameEngine engine;
    private boolean restart = false;

    // ==========================================================
    // Constructor
    // ==========================================================

    private AbstractGameEngine engine;

    public RiverGUI(AbstractGameEngine engine) {
        this.engine = engine;
        addMouseListener(this);
    }

    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Ensure that the panel is properly cleared

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Item item : engine.getItems()) {
            paintItem(g, item);
        }

        paintBoat(g);

        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        } else if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }

        paintMessage(message, g);
        if (restart) {
            paintRestartButtons(g);  // Paint the Farmer and Monster buttons
        }
    }


    private void paintItem(Graphics g, Item item) {
        Rectangle rect = getItemRectangle(item);  // Get the rectangle for the item
        Color color = engine.getItemColor(item);  // Get the item's color
        String label = engine.getItemLabel(item);  // Get the item's label

        if (rect != null) {
            paintRectangle(g, color, label, rect);  // Paint the rectangle
        }
    }

    private Rectangle getItemRectangle(Item item) {
        int index = item.ordinal();  // Get the index for the item (0 to 3)
        Location location = engine.getItemLocation(item);  // Get the item's current location

        if (location.isAtStart()) {
            return new Rectangle(leftBaseX + dx[index], leftBaseY + dy[index], 50, 50);
        } else if (location.isAtFinish()) {
            return new Rectangle(rightBaseX + dx[index], rightBaseY + dy[index], 50, 50);
        } else if (location.isOnBoat()) {
            // Item is on the boat
            if (engine.getSeat1() == item) {
                return new Rectangle(boatX, boatY - 60, 50, 50);  // First seat
            } else if (engine.getSeat2() == item) {
                return new Rectangle(boatX + 60, boatY - 60, 50, 50);  // Second seat
            }
        }
        return null;
    }

    private void paintBoat(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(boatX, boatY, 110, 50);

        // Paint seat items in the boat
        Item seat1 = engine.getSeat1();
        Item seat2 = engine.getSeat2();

        if (seat1 != null) {
            paintItemInBoat(g, seat1, boatX, boatY - 60);
        }
        if (seat2 != null) {
            paintItemInBoat(g, seat2, boatX + 60, boatY - 60);
        }
    }

    private void paintItemInBoat(Graphics g, Item item, int x, int y) {
        Color color = engine.getItemColor(item);
        String label = engine.getItemLabel(item);
        Rectangle rect = new Rectangle(x, y, 50, 50);
        paintRectangle(g, color, label, rect);
    }

    private void paintMessage(String message, Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }

    private void paintRestartButtons(Graphics g) {
        g.setColor(Color.BLACK);

        // Draw Farmer button
        paintBorder(restartFarmerButtonRect, 3, g);
        paintRectangle(g, Color.GREEN, "Farmer", restartFarmerButtonRect);

        // Draw Monster button
        paintBorder(restartMonsterButtonRect, 3, g);
        paintRectangle(g, Color.ORANGE, "Monster", restartMonsterButtonRect);
    }


    private void paintRectangle(Graphics g, Color color, String label, Rectangle r) {
        g.setColor(color);
        g.fillRect(r.x, r.y, r.width, r.height);

        g.setColor(Color.BLACK);
        int fontSize = (r.height >= 40) ? 12 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontSize));

        FontMetrics fm = g.getFontMetrics();
        int labelX = r.x + (r.width - fm.stringWidth(label)) / 2;
        int labelY = r.y + ((r.height - fm.getHeight()) / 2) + fm.getAscent();
        g.drawString(label, labelX, labelY);
    }

    private void paintBorder(Rectangle r, int thickness, Graphics g) {
        g.fillRect(r.x - thickness, r.y - thickness, r.width + (2 * thickness), r.height + (2 * thickness));
    }

    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        Point clickPoint = e.getPoint();
        if (restart) {
            if (restartFarmerButtonRect.contains(clickPoint)) {
                engine = new FarmerGameEngine();  // Switch to Farmer engine
                engine.resetGame();
                updateBoatAndItems();
                restart = false;
                repaint();
            } else if (restartMonsterButtonRect.contains(clickPoint)) {
                engine = new MonsterGameEngine();  // Switch to Monster engine
                engine.resetGame();
                updateBoatAndItems();
                restart = false;
                repaint();
            }
            return;
        }

        // Handle clicking on items and boat
        for (Item item : engine.getItems()) {
            Rectangle itemRect = getItemRectangle(item);

            // Check if an item on the shore or boat was clicked
            if (itemRect != null && itemRect.contains(clickPoint)) {
                handleItemClick(item);
                return;  // Exit loop after handling the click
            }
        }

        // Handle clicking on the boat itself
        if (boatRectangle.contains(clickPoint)) {
            handleBoatClick();
        }
    }

    private void handleItemClick(Item item) {
        Location itemLocation = engine.getItemLocation(item);
        System.out.println("mun" + itemLocation);

        if (itemLocation.isOnBoat()) {
            // Unload the item if it's in the boat
            engine.unloadBoat(item);
        } else if (itemLocation.isAtStart() || itemLocation.isAtFinish()) {
            // Load the item onto the boat if it's on the shore
            engine.loadBoat(item);
            System.out.println("mun" + itemLocation);
        }

        repaint();
    }

    private void handleBoatClick() {
        // Get the boat's current location
        Location boatLocation = engine.getBoatLocation();
        List<Item> items = engine.getItems();  // Get the list of all items
        if (boatLocation == Location.START) {
            // Forward journey requires Farmer and another character
            System.out.println("boatlocation" + boatX + boatY);
            engine.rowBoat();  // Move boat to FINISH
            updateBoatAndItems();
        }
        // Check return journey conditions (FINISH -> START)
        else if (boatLocation == Location.FINISH) {
            engine.rowBoat();  // Move boat to START
            updateBoatAndItems();  // Update boat and characters' positions
//        }
        }
    }


    private void updateBoatAndItems() {

        if (engine.getBoatLocation() == Location.START) {
            boatX = 140;
        } else {
            boatX = 550;
        }

        boatRectangle = new Rectangle(boatX, boatY, 110, 50);
        repaint();
    }


    // Unused MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // ==========================================================
    // Startup Methods
    // ==========================================================

}

class GameController {

    private static JFrame frame;  // Declare the frame at the class level

    // Method to create and show the GUI
    private static void createAndShowGUI() {
        frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up buttons to choose between Farmer or Monster engines
        JButton farmerButton = new JButton("Farmer Mode");
        farmerButton.addActionListener(e -> startGame(new FarmerGameEngine()));

        JButton monsterButton = new JButton("Monster Mode");
        monsterButton.addActionListener(e -> startGame(new MonsterGameEngine()));

        // Add the buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(farmerButton);
        buttonPanel.add(monsterButton);

        // Add the button panel to the frame at the top (NORTH)
        frame.add(buttonPanel, BorderLayout.NORTH);

        // Initialize the game with Farmer mode by default
        RiverGUI newContentPane = new RiverGUI(new FarmerGameEngine());
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Set up the frame size and make it visible
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private static void startGame(AbstractGameEngine engine) {
        frame.getContentPane().removeAll();  // Clear the current content
        RiverGUI newContentPane = new RiverGUI(engine);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);  // Add the new content
        frame.revalidate();  // Refresh the frame
        frame.repaint();  // Repaint the frame
    }

    public static void main(String[] args) {
        // Invoke the creation of the GUI on the event-dispatching thread
        javax.swing.SwingUtilities.invokeLater(GameController::createAndShowGUI);
    }
}


