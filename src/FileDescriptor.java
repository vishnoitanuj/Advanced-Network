import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class FileDescriptor {
    String inputFileName;
    String outputFileName;
    String receivedFileName;

    FileReader input;
    FileWriter output;
    FileInputStream receivedData;
}
