import components.waitingline.WaitingLine;
import components.waitingline.WaitingLine1;

/**
 * Demonstrates customer service: merge overflow arrivals, cancel a request,
 * prioritize an urgent request, and serve the remaining customers.
 */
public final class HelpDeskDemo {

    /** No instances needed. */
    private HelpDeskDemo() {
    }

    /**
     * Runs the help desk scenario.
     *
     * @param args
     *            unused command-line arguments
     */
    public static void main(String[] args) {
        WaitingLine<String> customers = new WaitingLine1<String>();
        customers.addToLine("Alice");
        customers.addToLine("Ben");
        customers.addToLine("Cara");

        WaitingLine<String> overflow = customers.newInstance();
        overflow.addToLine("Dev");
        overflow.addToLine("Eva");
        customers.append(overflow);
        System.out.println("Arrivals: " + customers);
        System.out.println("Cancelled: " + customers.remove("Ben"));

        customers.moveToFront("Eva");
        customers.insertAtFront("Urgent walk-in");
        System.out.println("Next customer: " + customers.front());
        System.out.println("Cara's position: " + customers.pos("Cara"));
        while (customers.length() > 0) {
            System.out.println("Serving: " + customers.removeFromLine());
        }
        System.out.println("Remaining customers: " + customers.length());
    }
}
