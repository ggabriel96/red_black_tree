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
    int k;
    Node p, l, r;
    boolean red;

    public Node(int k, boolean red) {
        this.k = k;
        this.red = red;
        this.p = this.l = this.r = Tree.nil;
    }

    public Node find(int k) {
        if (k < this.k && this.l != Tree.nil) return this.l.find(k);
        else if (k > this.k && this.r != Tree.nil) return this.r.find(k);
        else return this;
    }

    public Node min() {
        if (this.l != Tree.nil) return this.l.min();
        else return this;
    }

    public Node max() {
        if (this.r != Tree.nil) return this.r.max();
        else return this;
    }

    public Node predecessor() {
        if (this.l != Tree.nil) return this.l.max();
        else return this;
    }

    public Node successor() {
        if (this.r != Tree.nil) return this.r.min();
        else return this;
    }

    public int size() {
        int size = 1;
        if (this.r != Tree.nil) size += this.r.size();
        if (this.l != Tree.nil) size += this.l.size();
        return size;
    }

    public int depth() {
        if (this.p != Tree.nil) return 1 + this.p.depth();
        else return 0;
    }

    public int height() {
        if (this.l != Tree.nil && this.r != Tree.nil) {
            return 1 + Math.max(this.l.height(), this.r.height());
        }
        else if (this.l != Tree.nil) {
            return 1 + this.l.height();
        }
        else if (this.r != Tree.nil) {
            return 1 + this.r.height();
        }
        else return 0;
    }

    public void inorderWalk() {
        if (this.l != Tree.nil) this.l.inorderWalk();
        { // actual printing
            if (this.l != Tree.nil) System.out.print(this.l.k);
            else System.out.print("Tree.nil");

            if (this.p != Tree.nil) System.out.print("\t<- " + this.k + " -> \t");
            else System.out.print("\t<< " + this.k + " >> \t");

            if (this.r != Tree.nil) System.out.println(this.r.k);
            else System.out.println("Tree.nil");
        }
        if (this.r != Tree.nil) this.r.inorderWalk();
    }

    public void graph() {
        if (this.l != Tree.nil) {
            System.out.println(this.k + " -> " + this.l.k + " [label = \" l\"];");
            this.l.graph();
        }
        else {
            System.out.println(this.k + " -> nil;");
        }
        if (this.r != Tree.nil) {
            System.out.println(this.k + " -> " + this.r.k + " [label = \" r\"];");
            this.r.graph();
        }
        else {
            System.out.println(this.k + " -> nil;");
        }
    }
}
