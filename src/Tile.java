public class Tile implements ITile {
    protected String shape;
    protected Position position;

    public Tile() {
        this(new Position(0, 0), ".");
    }

    public Tile(Position position) {
       this(position, ".");
    }

    public Tile(Position position, String shape) {
        this.position = position;
        this.shape = shape;
    }

    public void setPosition(int x, int y) {
        position.setCoordinates(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public String getShape() {
        return shape;
    }
}
