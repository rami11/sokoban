public class RedBox extends Shape implements Tile {

    public RedBox(Position position) {
        super(position, "X");
    }

    public void goTo(Position newPosition) {
        setPosition(newPosition);
    }

    public Position lookAt(Position newPosition) {
        return newPosition;
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
