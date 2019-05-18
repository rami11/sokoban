import java.io.*;
import java.util.Arrays;

public class Canvas {

    private int width, length;
    private ITile[][] canvas;

    private Controller controller;

    public Canvas(File levelFile, Controller controller) {
        this.controller = controller;

        loadLevel(levelFile);
        System.out.println();
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public ITile[][] getCanvas() {
        return canvas;
    }

    private void loadLevel(File levelFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(levelFile))) {
            String[] dimensions = reader.readLine().split(" ");
            width = Integer.parseInt(dimensions[0]);
            length = Integer.parseInt(dimensions[1]);

            canvas = new ITile[width][length];

            int rowIndex = 0;
            String line;
            while ((line = reader.readLine()) != null) {

                String[] tileShapes = line.split(" ");
                for (int columnIndex = 0; columnIndex < tileShapes.length; columnIndex++) {
                    switch (tileShapes[columnIndex]) {
                        case "*":
                            Edge edge = new Edge(new Position(rowIndex, columnIndex));
                            controller.addEdge(edge);
                            canvas[rowIndex][columnIndex] = edge;
                            break;
                        case "o":
                            Target target = new Target(new Position(rowIndex, columnIndex));
                            controller.addTarget(target);
                            canvas[rowIndex][columnIndex] = target;
                            break;
                        case "x":
                            Box box = new Box(new Position(rowIndex, columnIndex));
                            controller.addBox(box);
                            canvas[rowIndex][columnIndex] = box;
                            break;
                        case ".":
                            Tile tile = new Tile(new Position(rowIndex, columnIndex));
                            controller.addTile(tile);
                            canvas[rowIndex][columnIndex] = tile;
                            break;
                        case "@":
                            Player player = new Player(new Position(rowIndex, columnIndex));
                            controller.setPlayer(player);
                            canvas[rowIndex][columnIndex] = player;
                            break;
                    }
                }
                rowIndex++;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTile(Position position) {
        return canvas[position.getX()][position.getY()] instanceof Tile;
    }

    public boolean isBox(Position position) {
        return canvas[position.getX()][position.getY()] instanceof Box;
    }

    public void show() {
        System.out.println(this);
    };

    @Override
    public String toString() {
        StringBuilder canvasBuilder = new StringBuilder();
        Arrays.asList(canvas).forEach(row -> {
            Arrays.asList(row).forEach(tile -> canvasBuilder.append(tile).append(" "));
            canvasBuilder.append("\n");
        });

        return canvasBuilder.toString();
    }
}
