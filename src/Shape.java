public class Shape implements ITile {
    protected String shape;
    protected Position position;

    public Shape() {
        this(new Position(0, 0), "S");
    }

    public Shape(Position position) {
        this(position, "S");
    }

    public Shape(Position position, String shape) {
        this.position = position;
        this.shape = shape;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public String getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return this.shape;
    }
}
