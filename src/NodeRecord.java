import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Logger;

public class NodeRecord {

    public int numNodes = 0;
    public FileDescriptor channels[];
    public int topologyLinks[][];
    public int nodeNotResponding[];
    private static final Logger LOGGER = Logger.getLogger("NodeRecord");


    public NodeRecord() {
        numNodes = 0;
        topologyLinks = new int[10][10];
        nodeNotResponding = new int[10];
    }

    public void createChannels() {
        channels = new FileDescriptor[10];
        for (int i = 0; i < numNodes; i++) {
            channels[i] = new FileDescriptor();
            channels[i].inputFileName = "output_" + i;
            channels[i].outputFileName = "input_" + i;

            try {
                File inputFile = new File(channels[i].inputFileName);
                if(inputFile.createNewFile())
                    LOGGER.info(channels[i].inputFileName+" file created");
                else
                    LOGGER.warning(channels[i].inputFileName+" file already exists");
                channels[i].input = new FileReader(channels[i].inputFileName);

                File outFile = new File(channels[i].outputFileName);
                if(outFile.createNewFile())
                    LOGGER.info(channels[i].outputFileName+" file created");
                else
                    LOGGER.warning(channels[i].outputFileName+" file already exists");
                channels[i].output = new FileWriter(channels[i].outputFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
