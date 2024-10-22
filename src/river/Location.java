package river;

public enum Location {
    START, FINISH, BOAT;

    public boolean isOnBoat() {
        return this == BOAT;
    }

    public boolean isAtFinish() {
        return this == FINISH;
    }

    public boolean isAtStart() {
        return this == START;
    }
}
