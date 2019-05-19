public class Player extends Shape implements ITile {

    public Player(Position position) {
        super(position, "R");
    }

    public void goTo(Position newPosition) {
        setPosition(newPosition);
    }

    public Position lookUp() {
        return new Position(getPositionX() - 1, getPositionY());
    }

    public Position lookDown() {
        return new Position(getPositionX() + 1, getPositionY());
    }

    public Position lookLeft() {
        return new Position(getPositionX(), getPositionY() - 1);
    }

    public Position lookRight() {
        return new Position(getPositionX(), getPositionY() + 1);
    }
}
