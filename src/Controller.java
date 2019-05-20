import java.io.File;
import java.util.*;

public class Controller {
    private static final String[] LEVELS = {
            "resource/level1.txt",
            "resource/level2.txt",
            "resource/level10.txt",
    };

    private Player player;
    private List<Edge> edges;
    private List<Ground> tiles;

    private List<Target> targets;

    private Map<Position, Box> boxes;
    private Map<Position, RedBox> redBoxes;

    private Canvas canvas;

    public Controller() {
        edges = new ArrayList<>();
        tiles = new ArrayList<>();
        boxes = new HashMap<>();
        targets = new ArrayList<>();
        redBoxes = new HashMap<>();

        int levelNo = 0;

        while (true) {
            System.out.println("Level " + levelNo + ":\n");
            this.canvas = new Canvas(new File(LEVELS[levelNo]), this);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                String action = scanner.nextLine();

                switch (action) {
                    case "k":
                    case "w":
                        attemptStepUp(player.lookUp());
                        break;
                    case "j":
                    case "s":
                        attemptStepDown(player.lookDown());
                        break;
                    case "l":
                    case "d":
                        attemptStepRight(player.lookRight());
                        break;
                    case "h":
                    case "a":
                        attemptStepLeft(player.lookLeft());
                        break;
                    case "q":
                        System.exit(0);
                        break;
                    default:
                        //ignore
                }
                refresh();
                if (targets.size() == redBoxes.size()) {
                    System.err.println("Well done!\n");
                    levelNo++;
                    break;
                }
            }
        }
    }

    public void refresh() {
        Tile[][] canvas = this.canvas.getCanvas();

        tiles.forEach(tile -> canvas[tile.getPositionX()][tile.getPositionY()] = tile);
        targets.forEach(target -> canvas[target.getPositionX()][target.getPositionY()] = target);
        edges.forEach(edge -> canvas[edge.getPositionX()][edge.getPositionY()] = edge);

        boxes.values().forEach(box -> canvas[box.getPositionX()][box.getPositionY()] = box);
        redBoxes.values().forEach(redBox -> canvas[redBox.getPositionX()][redBox.getPositionY()] = redBox);
        canvas[player.getPositionX()][player.getPositionY()] = player;

        this.canvas.show();
    }

    private void attemptStepUp(Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isGround(newPosition) || canvas.isTarget(newPosition)) {
                player.goTo(newPosition);
            } else if (canvas.isBox(newPosition)) {
                Box box = boxes.get(player.lookUp());
                if (box != null) {
                    attemptPushBox(box.getPosition(), box.lookUp());
                }
            } else if (canvas.isRedBox(newPosition)) {
                RedBox redBox = redBoxes.get(player.lookUp());
                if (redBox != null) {
                    attemptPushRedBox(redBox.getPosition(), redBox.lookUp());
                }
            }
        }
    }

    private void attemptStepDown(Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isGround(newPosition) || canvas.isTarget(newPosition)) {
                player.goTo(newPosition);
            } else if (canvas.isBox(newPosition)) {
                Box box = boxes.get(player.lookDown());
                if (box != null) {
                    attemptPushBox(box.getPosition(), box.lookDown());
                }
            } else if (canvas.isRedBox(newPosition)) {
                RedBox redBox = redBoxes.get(player.lookDown());
                if (redBox != null) {
                    attemptPushRedBox(redBox.getPosition(), redBox.lookDown());
                }
            }
        }
    }

    private void attemptStepLeft(Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isGround(newPosition) || canvas.isTarget(newPosition)) {
                player.goTo(newPosition);
            } else if (canvas.isBox(newPosition)) {
                Box box = boxes.get(player.lookLeft());
                if (box != null) {
                    attemptPushBox(box.getPosition(), box.lookLeft());
                }
            } else if (canvas.isRedBox(newPosition)) {
                RedBox redBox = redBoxes.get(player.lookLeft());
                if (redBox != null) {
                    attemptPushRedBox(redBox.getPosition(), redBox.lookLeft());
                }
            }
        }
    }

    private void attemptStepRight(Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isGround(newPosition) || canvas.isTarget(newPosition)) {
                player.goTo(newPosition);
            } else if (canvas.isBox(newPosition)) {
                Box box = boxes.get(player.lookRight());
                if (box != null) {
                    attemptPushBox(box.getPosition(), box.lookRight());
                }
            } else if (canvas.isRedBox(newPosition)) {
                RedBox redBox = redBoxes.get(player.lookRight());
                if (redBox != null) {
                    attemptPushRedBox(redBox.getPosition(), redBox.lookRight());
                }
            }
        }
    }


    private void attemptPushBox(Position oldPosition, Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isGround(newPosition)) {
                Box box = boxes.get(oldPosition);
                player.setPosition(oldPosition);
                box.setPosition(newPosition);

                boxes.remove(oldPosition);
                boxes.put(newPosition, box);

            } else if (canvas.isTarget(newPosition)) {
                Box box = boxes.get(oldPosition);
                player.setPosition(oldPosition);
                box.setPosition(newPosition);

                boxes.remove(oldPosition);
                redBoxes.put(newPosition, new RedBox(newPosition));
            }
        }
    }

    private void attemptPushRedBox(Position oldPosition, Position newPosition) {
        if (isInsideCanvas(newPosition)) {
            if (canvas.isGround(newPosition)) {
                RedBox redBox = redBoxes.get(oldPosition);
                player.setPosition(oldPosition);
                redBox.setPosition(newPosition);

                redBoxes.remove(oldPosition);
                boxes.put(newPosition, new Box(newPosition));

            } else if (canvas.isTarget(newPosition)) {
                RedBox redBox = redBoxes.get(oldPosition);
                player.setPosition(oldPosition);
                redBox.setPosition(newPosition);

                redBoxes.remove(oldPosition);
                redBoxes.put(newPosition, redBox);
            }
        }
    }


    public void addGround(Ground ground) {
        tiles.add(ground);
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

    public void addRedBox(RedBox redBox) {
        redBoxes.put(redBox.getPosition(), redBox);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private boolean isInsideCanvas(Position position) {
        return position.getX() < canvas.getWidth() && position.getY() < canvas.getLength();
    }

    public void clearTiles() {
        player = null;
        edges.clear();
        tiles.clear();
        targets.clear();
        boxes.clear();
        redBoxes.clear();
    }
}
