import java.io.File;
import java.util.*;

public class Controller {
    private static final String[] LEVELS = {
            "resource/level1.txt",
            "resource/level10.txt"
    };

    private Player player;
    private List<Edge> edges;
    private List<Tile> tiles;
    private Map<Position, Box> boxes;
    private List<Target> targets;

    private Canvas canvas;

    public Controller() {
        edges = new ArrayList<>();
        tiles = new ArrayList<>();
        boxes = new HashMap<>();
        targets = new ArrayList<>();

        int levelNo = 0;

        this.canvas = new Canvas(new File(LEVELS[levelNo]), this);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String action = scanner.nextLine();

            switch (action) {
                case "k":
                case "w":
                    attemptGoTo(player.lookUp());
                    Box box = boxes.get(player.lookUp());
                    if (box != null) {
                        attemptPush(box.getPosition(), box.lookUp());
                    }
                    break;
                case "j":
                case "s":
                    attemptGoTo(player.lookDown());
                    Box box1 = boxes.get(player.lookDown());
                    if (box1 != null) {
                        attemptPush(box1.getPosition(), box1.lookDown());
                    }
                    break;
                case "l":
                case "d":
                    attemptGoTo(player.lookRight());
                    Box box2 = boxes.get(player.lookRight());
                    if (box2 != null) {
                        attemptPush(box2.getPosition(), box2.lookRight());
                    }
                    break;
                case "h":
                case "a":
                    attemptGoTo(player.lookLeft());
                    Box box3 = boxes.get(player.lookLeft());
                    if (box3 != null) {
                        attemptPush(box3.getPosition(), box3.lookLeft());
                    }
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
                    //ignore
            }
            refresh();
        }
    }

    public void refresh() {
        ITile[][] canvas = this.canvas.getCanvas();

        tiles.forEach(tile -> canvas[tile.getPositionX()][tile.getPositionY()] = tile);
        targets.forEach(target -> canvas[target.getPositionX()][target.getPositionY()] = target);
        edges.forEach(edge -> canvas[edge.getPositionX()][edge.getPositionY()] = edge);

        boxes.values().forEach(box -> canvas[box.getPositionX()][box.getPositionY()] = box);
        canvas[player.getPositionX()][player.getPositionY()] = player;

        this.canvas.show();
    }

    private void attemptGoTo(Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isTile(newPosition) || canvas.isTarget(newPosition)) {
                player.goTo(newPosition);
            }
        }
    }

    private void attemptPush(Position oldPosition, Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isTile(newPosition) || canvas.isTarget(newPosition)) {
                Box box = boxes.get(oldPosition);
                player.setPosition(oldPosition);
                box.setPosition(newPosition);

            } else if (canvas.isTarget(newPosition)) {

            }
        }
    }

    private void attemptStepUp(Position newPosition) {
        attemptGoTo(newPosition);
    }

    private void attemptStepDown() {
        attemptGoTo(player.lookDown());
    }

    private void attemptStepLeft() {
        attemptGoTo(player.lookLeft());
    }

    private void attemptStepRight() {
        attemptGoTo(player.lookRight());
    }


    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void addBox(Box box) {
        boxes.put(box.getPosition(), box);
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
