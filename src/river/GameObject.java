package river;


import java.awt.*;

public class GameObject {

    private final String label;
    private Location location;  // Use the correct 'Location' enum from the river package
    private final Color color;
    private final boolean isDriver;

    // Constructor to initialize name, location, sound, and isDriver
    public GameObject(String label, Location location, Color color, boolean isDriver) {
        this.label = label;
        this.location = location;
        this.color = color;
        this.isDriver = isDriver;  // Farmer would have this set to true, others false
    }

    public String getLable() {

        return label;
    }

    public Location getLocation() {

        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }


    public Color getColor() {
        return color;
    }

    // Getter for isDriver
    public boolean isDriver() {
        return isDriver;
    }

}
