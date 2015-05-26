/*
 * This file is part of red_black_tree.
 *
 *  red_black_tree is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  red_black_tree is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with red_black_tree. If not, see <http://www.gnu.org/licenses/>.
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
