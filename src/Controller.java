import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private static final String[] LEVELS = {
            "resource/level1.txt",
            "resource/level10.txt"
    };

    private Player player;
    private List<Edge> edges;
    private List<Tile> tiles;
    private List<Box> boxes;
    private List<Target> targets;

    private Canvas canvas;

    public Controller() {
        edges = new ArrayList<>();
        tiles = new ArrayList<>();
        boxes = new ArrayList<>();
        targets = new ArrayList<>();

        int levelNo = 0;

        this.canvas = new Canvas(new File(LEVELS[levelNo]), this);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String action = scanner.nextLine();

            switch (action) {
                case "k":
                case "w":
                    stepUp();
                    break;
                case "j":
                case "s":
                    stepDown();
                    break;
                case "l":
                case "d":
                    stepRight();
                    break;
                case "h":
                case "a":
                    stepLeft();
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
            }
        }
    }

    public void refresh() {
        ITile[][] canvas = this.canvas.getCanvas();

        tiles.forEach(tile -> canvas[tile.getPositionX()][tile.getPositionY()] = tile);
        targets.forEach(target -> canvas[target.getPositionX()][target.getPositionY()] = target);
        boxes.forEach(box -> canvas[box.getPositionX()][box.getPositionY()] = box);
        edges.forEach(edge -> canvas[edge.getPositionX()][edge.getPositionY()] = edge);

        canvas[player.getPositionX()][player.getPositionY()] = player;

        this.canvas.show();
    }

    private void attemptGoTo(Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isTile(newPosition)) {
                player.goTo(newPosition);
            } else if (canvas.isBox(newPosition)) {

            }
        }
        refresh();
    }

    private void stepUp() {
        attemptGoTo(player.lookUp());
    }

    private void stepDown() {
        attemptGoTo(player.lookDown());
    }

    private void stepLeft() {
        attemptGoTo(player.lookLeft());
    }

    private void stepRight() {
        attemptGoTo(player.lookRight());
    }


    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public void addTarget(Target target) {
        targets.add(target);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private boolean isInsideCanvas(Position position) {
        return position.getX() < canvas.getWidth() && position.getY() < canvas.getLength();
    }

}
