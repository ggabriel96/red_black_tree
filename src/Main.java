/*
 * This file is part of binary_search_tree.
 *
 *  binary_search_tree is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  binary_search_tree is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with binary_search_tree. If not, see <http://www.gnu.org/licenses/>.
 */

class Main {
    public static void main(String args[]) {
        /*Tree t = new Tree(5);
        t.add(1);
        t.add(0);
        t.add(2);
        t.add(3);
        t.add(5); // doesn't add duplicates
        t.add(6);
        t.add(7);
        t.add(10);
        t.add(4);
        t.add(25);
        t.add(23);
        t.add(11);
        t.add(15);
        t.add(17);
        t.add(8);
        t.add(9);*/
        /*System.out.println("Original tree:");
        t.inorderWalk();
        System.out.println();*/

        Tree t = new Tree(7);
        t.add(2);
        t.add(1);
        t.add(5);
        t.add(4);
        t.add(11); // doesn't add duplicates
        t.add(8);
        t.add(14);
        t.add(15);

        t.remove(t.find(14));

        t.graph();

        /*System.out.println("t.size: " + t.size() + " node(s)");
        System.out.println("t.depth: " + t.depth());
        System.out.println("t.height: " + t.height());
        System.out.println("t.min: " + t.min().k);
        System.out.println("t.max: " + t.max().k);
        System.out.println("t.find(0): " + t.find(0).k);*/

        //System.out.println(t);
    }
}
