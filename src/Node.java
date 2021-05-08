import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Node {
    public int ID;
    public int duration;
    public int dest;
    public String message;

    private FileDescriptor channel;

    private static final Logger LOGGER = Logger.getLogger("Node");

    public Node(int ID, int duration, int dest, String message) {
        this.ID = ID;
        this.duration = duration;
        this.dest = dest;
        this.message = message;
    }

    public Node() {
        this.ID = -1;
        this.duration = 0;
        this.dest = -1;
        this.message = "";
    }

    private void setChannel() {
        channel.inputFileName = "input_" + this.ID;
        channel.outputFileName = "output_" + this.ID;
        channel.receivedFileName = this.ID + "_received";

        try {
            channel.input = new FileReader(channel.inputFileName);
            channel.output = new FileWriter(channel.outputFileName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, () -> "Exception in creating files");
        }
    }

    public void helloProtocol() {
        try {
            channel.output.write("Hello " + this.ID);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.output.close();
                channel.output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 4 || args.length > 5) {
            LOGGER.severe(() -> "Incorrect Arguments");
            System.exit(0);
        }
        int arguments[] = new int[3];
        for (int i = 0; i < 3; i++)
            arguments[i] = Integer.parseInt(args[i]);

        String data;
        if (arguments[2] == -1)
            data = "";
        else
            data = args[4];

        Node node = new Node(arguments[0], arguments[1], arguments[2], data);
        for (int i = 0; i < node.duration; i++) {
            if (i % 30 == 0)
                node.helloProtocol();
            Thread.sleep(1000);
            System.out.println("Node " + node.ID + " Done");
        }
    }
}
