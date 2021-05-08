package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;

public class Node {
    public int ID;
    public int duration;
    public int dest;
    public String message;

    private FileDescriptor channel;
    private static final Logger LOGGER = Logger.getLogger("Node");

    public Node(int ID, int duration, int dest, String message){
        this.ID = ID;
        this.duration = duration;
        this.dest = dest;
        this.message = message;
    }

    private void setChannels() {
        try {
            File resultsDirectory = new File(OUTPUT_DIRECTORY);
            if (!resultsDirectory.exists()) {
                resultsDirectory.mkdir();
            }

            //FILES CREATED BY NODE
            File inputFile = new File(OUTPUT_DIRECTORY + "input_" + nodeId);
            
            File outputFile = new File(OUTPUT_DIRECTORY + "output_" + nodeId);
            File receivedFile = new File(OUTPUT_DIRECTORY + nodeId + "_received");

            inputFile.createNewFile();
            outputFile.createNewFile();
            receivedFile.createNewFile();

            inputFile = new BufferedReader(new FileReader(inputFile));
            outputFile = new BufferedWriter(new FileWriter(outputFile, true));
            receivedFile = new BufferedWriter(new FileWriter(receivedFile));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, () -> "Exception in creating files");
        }
    }
}
