package edu.estu;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    public static void main(String[] args) {
        Options option = new Options();
        CmdLineParser cmdLineParser = new CmdLineParser(option);
        try {
            cmdLineParser.parseArgument(args);
        }
        catch (CmdLineException e){
            System.err.println(e.getMessage());
            cmdLineParser.printUsage(System.err);
            return;
        }
        Algorithms algorithms = new Algorithms();
        algorithms.initializeAlgorithms(option.initial);
    }
}