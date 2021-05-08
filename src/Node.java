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
}
