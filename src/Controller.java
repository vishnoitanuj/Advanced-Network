import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private int duration;
    private FileDescriptor channel;
    private NodeRecord nodes;

    private static final Logger LOGGER = Logger.getLogger("Controller");

    public Controller(int duration) {
        this.duration = duration;
        setChannel();
        createNodeChannels();
    }

    public void parseString(String line) {
        int sourceNode = Integer.parseInt(line.split("\\s")[0]);
        int destNode = Integer.parseInt(line.split("\\s")[2]);

        if (sourceNode > nodes.numNodes || destNode > nodes.numNodes) {
            if (sourceNode > destNode)
                nodes.numNodes = sourceNode;
            else
                nodes.numNodes = destNode;
        }

        nodes.topologyLinks[sourceNode][destNode] = 1;
    }

    public void createNodeChannels(){
        String line;
        BufferedReader reader = new BufferedReader(channel.input);
        try {
            while ((line = reader.readLine()) != null) {
                parseString(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        nodes.createChannels();
    }

    public void setChannel(){
        channel.inputFileName = "topology";
        channel.outputFileName = "";
        try {
            channel.input = new FileReader(channel.inputFileName);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Unable to read topology file");
        }
    }

    public void sendToNeighboursData() {
        for (int i = 0; i < nodes.numNodes; i++) {
            try {
                BufferedReader reader = new BufferedReader(nodes.channels[i].input);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    for (int j = 0; j < nodes.numNodes; j++) {
                        if (nodes.topologyLinks[i][j] == 1) {
                            try {
                                nodes.channels[i].output.write(line);
                            } catch (Exception e) {
                                LOGGER.log(Level.SEVERE, "Error in writing line = " + line + " to output file - " + nodes.channels[i].outputFileName);
                            } finally {
                                nodes.channels[i].output.close();
                                nodes.channels[i].output.flush();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            LOGGER.severe(() -> "Need two arguments");
            System.exit(0);
        }

        int duration = Integer.parseInt(args[1]);

        Thread.sleep(1000);
        System.out.println();
        Controller controller = new Controller(duration);
        for(int i=0;i< controller.duration;i++){
            controller.sendToNeighboursData();
            Thread.sleep(1000);
        }
        System.out.println("Controller Done");
    }
}
