import sun.jvm.hotspot.runtime.Thread;

/**
 * Start all the processes from one place by invoking the main functions
 */

public class Controller {
    public static void main(String[] args) {
        ReceivingServer.main(new String[0]);
        TagSimulation.main(new String[0]);
        FileAccess.main(new String[0]);
        Visualization.main(new String[0]);
    }
}
