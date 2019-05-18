public class Player extends Tile implements ITile {

    public Player(Position position) {
        super(position, "@");
    }

    public void stepRight() {
        position.incrementY();
    }

    public void stepLeft() {
        position.decrementY();
    }

    public void stepUp() {
        position.decrementX();
    }

    public void stepDown() {
        position.incrementX();
    }
}
