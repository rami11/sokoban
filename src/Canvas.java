import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private static final int DEFAULT_LENGTH = 50;
    private static final int DEFAULT_WIDTH = 50;

    private List<List<ITile>> tiles;
    private int length;
    private int width;

    public Canvas(File levelFile) {
        this(DEFAULT_LENGTH, DEFAULT_WIDTH, levelFile);
    }

    public Canvas(int width, int length, File levelFile) {
        this.width = width;
        this.length = length;

        tiles = new ArrayList<>();

        loadLevel(levelFile);
        System.out.println();
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    private void loadLevel(File levelFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(levelFile))) {

            int rowIndex = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                List<ITile> rowList = new ArrayList<>();

                String[] tileShapes = line.split(" ");
                for (int columnIndex = 0; columnIndex < tileShapes.length; columnIndex++) {
                    switch (tileShapes[columnIndex]) {
                        case "*":
                            rowList.add(new Edge(new Position(rowIndex, columnIndex)));
                            break;
                        case "o":
                            rowList.add(new Target(new Position(rowIndex, columnIndex)));
                            break;
                        case "@":
                            rowList.add(new Player(new Position(rowIndex, columnIndex)));
                            break;
                        case "x":
                            rowList.add(new Box(new Position(rowIndex, columnIndex)));
                            break;
                        case ".":
                            rowList.add(new Tile(new Position(rowIndex, columnIndex)));
                            break;
                    }
                }
                tiles.add(rowList);
                rowIndex++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder canvasBuilder = new StringBuilder(length * width);
        tiles.forEach(row -> {
            row.forEach(tile -> canvasBuilder.append(tile).append(" "));
            canvasBuilder.append("\n");
        });

        return canvasBuilder.toString();
    }
}
