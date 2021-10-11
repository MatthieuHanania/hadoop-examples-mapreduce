package com.opstty;

import com.opstty.job.*;
import org.apache.hadoop.util.ProgramDriver;

public class AppDriver {
    public static void main(String argv[]) {
        int exitCode = -1;
        ProgramDriver programDriver = new ProgramDriver();

        try {
            programDriver.addClass("wordcount", WordCount.class,
                    "A map/reduce program that counts the words in the input files.");

            programDriver.addClass("list_district", list_district.class,
                    "A map/reduce program that display list of district");

            programDriver.addClass("species", species.class,
                    "A map/reduce program that displays the list of different species trees");

            programDriver.addClass("NumTrees", NumTrees.class,
                    "A map/reduce program that count the number of Trees by each kind");

            programDriver.addClass("TallKind", TallKind.class,
                    "A map/reduce program that display the tallest trees for each kind of trees");

            programDriver.addClass("SortHeight", SortHeight.class,
                    "A map/reduce program that sort the trees height");

            programDriver.addClass("OldTree", OldTree.class,
                    "A map/reduce program that display the district contraining the oldest tree");


            exitCode = programDriver.run(argv);


        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.exit(exitCode);
    }
}
