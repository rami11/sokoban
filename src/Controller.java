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
            refresh();

            String action = scanner.nextLine();

            switch (action) {
                case "k":
                    stepUp();
                    break;
                case "j":
                    stepDown();
                    break;
                case "l":
                    stepRight();
                    break;
                case "h":
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

    private void stepUp() {
        Position newPosition = new Position(player.getPositionX() - 1, player.getPositionY());
        if (canvas.isTile(newPosition)) {
            player.stepUp();
            refresh();
        }
    }

    private void stepDown() {
        Position newPosition = new Position(player.getPositionX() + 1, player.getPositionY());
        if (canvas.isTile(newPosition)) {
            player.stepDown();
            refresh();
        }
    }

    private void stepLeft() {
        Position newPosition = new Position(player.getPositionX(), player.getPositionY() - 1);
        if (canvas.isTile(newPosition)) {
            player.stepLeft();
            refresh();
        }
    }

    private void stepRight() {
        Position newPosition = new Position(player.getPositionX(), player.getPositionY() + 1);
        if (canvas.isTile(newPosition)) {
            player.stepRight();
            refresh();
        }
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

}
