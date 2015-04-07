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

class Node {
    public int key;
    public boolean red;
    public Node p, left, right;

    public Node(int key, boolean red) {
        this.key = key;
        this.red = red;
        this.p = this.left = this.right = Tree.nil;
    }

    public Node find(int key) {
        if (key < this.key && this.left != Tree.nil) return this.left.find(key);
        else if (key > this.key && this.right != Tree.nil) return this.right.find(key);
        else return this;
    }

    public Node min() {
        if (this.left != Tree.nil) return this.left.min();
        else return this;
    }

    public Node max() {
        if (this.right != Tree.nil) return this.right.max();
        else return this;
    }

    public Node predecessor() {
        if (this.left != Tree.nil) return this.left.max();
        else return this;
    }

    public Node successor() {
        if (this.right != Tree.nil) return this.right.min();
        else return this;
    }

    public int size() {
        int size = 1;
        if (this.right != Tree.nil) size += this.right.size();
        if (this.left != Tree.nil) size += this.left.size();
        return size;
    }

    public int depth() {
        if (this.p != Tree.nil) return 1 + this.p.depth();
        else return 0;
    }

    public int height() {
        if (this.left != Tree.nil && this.right != Tree.nil) {
            return 1 + Math.max(this.left.height(), this.right.height());
        }
        else if (this.left != Tree.nil) {
            return 1 + this.left.height();
        }
        else if (this.right != Tree.nil) {
            return 1 + this.right.height();
        }
        else return 0;
    }

    public void inorderWalk() {
        if (this.left != Tree.nil) this.left.inorderWalk();
        System.out.println(this.key);
        if (this.right != Tree.nil) this.right.inorderWalk();
    }

    public void graph() {
        if (this.red) { // coloring
            System.out.println("\t" + this.key + " [style = filled, fillcolor = red];");
        } else {
            System.out.println("\t" + this.key + " [style = filled, fillcolor = black, fontcolor = white];");
        }

        if (this.left != Tree.nil) {
            System.out.println("\t" + this.key + " -> " + this.left.key + " [label = \" left\"];");
            this.left.graph();
        }
        else {
            System.out.println("\t" + this.key + " -> nil [label = \" left\"];");
        }

        if (this.right != Tree.nil) {
            System.out.println("\t" + this.key + " -> " + this.right.key + " [label = \" right\"];");
            this.right.graph();
        }
        else {
            System.out.println("\t" + this.key + " -> nil [label = \" right\"];");
        }
    }
}
