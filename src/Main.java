/**
 * This file is part of red_black_tree.
 *
 * I dedicate any and all copyright interest in this software to the
 * public domain. I make this dedication for the benefit of the public at
 * large and to the detriment of my heirs and successors. I intend this
 * dedication to be an overt act of relinquishment in perpetuity of all
 * present and future rights to this software under copyright law.
 *
 * For more information, please refer to <http://unlicense.org/>
 */

import java.util.*;

class Main {
    public static final int ADD_COUNT = 100;
    public static final int SEARCH_COUNT = 5;
	// public static final long MAX = 4294967296L; // 2^32
	public static final int MAX = 2147483647; // 2^31 - 1

	public static void main(String[] args) {
		Random prn = new Random(System.nanoTime());
		long initialTime = 0, startTime = 0, avgTime = 0, totalTime = 0;
		RBTree t = null, rbt = new RBTree();
        RBNode foundNode = null;
		int i, fromKey, searchKey;

        startTime = avgTime = 0;
		initialTime = System.nanoTime();
        System.out.println("\n*** Inserting " + ADD_COUNT + " nodes ***\n");
		for (i = 0; i < ADD_COUNT; i++) {
			startTime = System.nanoTime();
			rbt.add(prn.nextInt(MAX));
			avgTime += System.nanoTime() - startTime;
		}
        System.out.println("Average insertion time (ns): " + avgTime / ADD_COUNT);
		System.out.printf("Average insertion time (s): %.10f\n", (avgTime / ADD_COUNT / 10e9));
        System.out.println("-------------------------------------------------");

        startTime = avgTime = 0;
        fromKey = 1_000_000;
        System.out.println("\n*** Searching next 50 nodes from key " + fromKey + " ***\n");
        startTime = System.nanoTime();
        t = rbt.find50(fromKey);
        avgTime += System.nanoTime() - startTime;
        System.out.println("Average 50-search time (ns): " + avgTime / ADD_COUNT);
		System.out.printf("Average 50-search time (s): %.10f\n", (avgTime / ADD_COUNT / 10e9));
        //t.graph();
        System.out.println("-------------------------------------------------");

        startTime = avgTime = 0;
        System.out.println("\n*** Searching " + SEARCH_COUNT + " nodes ***\n");
        for (i = 0; i < SEARCH_COUNT; i++) {
            searchKey = prn.nextInt(MAX);
            System.out.println("Searching for " + searchKey);
            startTime = System.nanoTime();
            foundNode = rbt.find(searchKey);
            avgTime += System.nanoTime() - startTime;
            System.out.println(foundNode.key == searchKey ? "* Found " + searchKey : "# " + searchKey + " not found");
            System.out.println();
        }
        System.out.println("Average searching time (ns): " + avgTime / ADD_COUNT);
		System.out.printf("Average searching time (s): %.10f\n", (avgTime / ADD_COUNT / 10e9));
        System.out.println("-------------------------------------------------");

		totalTime = System.nanoTime() - initialTime;
        System.out.println();
		System.out.println("Total time (ns): " + totalTime);
		System.out.println("Total time (s): " + (totalTime / 10e9));
	}
}
