import java.io.File;
import java.util.Scanner;

public class Controller {
    private static final String[] LEVELS = {
            "resource/level1.txt",
            "resource/level10.txt"
    };

    private Canvas canvas;
    private Player player;

    public Controller() {
        int levelNo = 0;

        this.canvas = new Canvas(new File(LEVELS[levelNo]));

        Scanner scanner = new Scanner(System.in);

        renderGame();

        while (true) {
            renderGame();

            String action = scanner.nextLine();

            /*switch (action) {
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
                default:*/
        }
    }

    private void renderGame() {
        System.out.println(canvas);
    }

    private boolean isThereRoomUp() {
        return player.getX() > 0;
    }

    private boolean isThereRoomDown() {
        return player.getX() < canvas.getWidth() - 1;
    }

    private boolean isThereRoomRight() {
        return player.getY() < canvas.getLength() - 1;
    }

    private boolean isThereRoomLeft() {
        return player.getY() > 0;
    }
}
