package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Controller {

    //Hash map set-up for topology file
    HashMap<String, Set<String>> map = new HashMap<>();

    //Hash map set up for Buffered reader
    HashMap<String, BufferedReader> inputStreams = new HashMap<>();

    //Hash map set up for BufferWriter
    HashMap<String, BufferedWriter> outputStreams = new HashMap<>();

    private void createStreams() throws IOException {

            //For Nodes to read output file
        for (String node : map.keySet()) {
            System.out.println(node);
            BufferedReader reader =
                    new BufferedReader(new FileReader("src/files/output_"+node));


            // Initializing BufferedWriter
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/files/input_2"+node));
                inputStreams.put(node,reader);
                outputStreams.put(node,bufferedWriter);
        }
        System.out.println(inputStreams);
        System.out.println(outputStreams);
    }



    private void readTopology() throws Exception {
        BufferedReader reader =
                new BufferedReader(new FileReader("src/topology"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] splitLine = line.split(" ");
            if (map.containsKey(splitLine[0])) {
                map.get(splitLine[0]).add(splitLine[1]);
            } else {
                HashSet<String> h = new HashSet<String>();
                h.add(splitLine[1]);
                map.put(splitLine[0], h);

            }

            // System.out.println(line.split(" ")[0]);
            // System.out.println(line.split(" ")[1]);


        }
        // System.out.println(map);


    }


    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        controller.readTopology();
        controller.createStreams();

    }

}

