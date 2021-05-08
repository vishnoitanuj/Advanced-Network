import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class NodeRecord {

    public int numNodes = 0;
    public FileDescriptor channels[];
    public int topologyLinks[][];
    public int nodeNotResponding[];


    public NodeRecord(){
        numNodes = 0;
        topologyLinks = new int[numNodes][numNodes];
        nodeNotResponding = new int[numNodes];
    }

    public void createChannels(){
        channels = new FileDescriptor[numNodes];
        for(int i=0;i<numNodes;i++){
            channels[i].inputFileName = "output_"+i;
            channels[i].outputFileName = "input_"+i;

            try {
                channels[i].input = new FileReader(channels[i].inputFileName);
                channels[i].output = new FileWriter(channels[i].outputFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
