import components.waitingline.WaitingLine;
import components.waitingline.WaitingLine1;

/**
 * Demonstrates players taking turns in a game. After a turn, the player rejoins
 * the back of the line.
 */
public final class RoundRobinDemo {

    /** No instances needed. */
    private RoundRobinDemo() {
    }

    /**
     * Gives each player two turns.
     *
     * @param args
     *            unused command-line arguments
     */
    public static void main(String[] args) {
        WaitingLine<String> players = new WaitingLine1<String>();
        players.addToLine("Alice");
        players.addToLine("Ben");
        players.addToLine("Cara");

        int totalTurns = players.length() * 2;
        for (int turn = 1; turn <= totalTurns; turn++) {
            String player = players.removeFromLine();
            System.out.println("Turn " + turn + ": " + player);
            players.addToLine(player);
        }
        System.out.println("Next player: " + players.front());
    }
}
