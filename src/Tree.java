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

class Tree {
    Node root;
    public static Node nil = new Node(0, false);

    public Tree(int k) {
        this.root = new Node(k, false);
    }

    public Node find(int k) {
        return this.root.find(k);
    }

    public void add(int k) {
        Node n = this.root.find(k);
        if (k < n.k) {
            n.l = new Node(k, true);
            n.l.p = n;
            this.addFix(n.l);
        }
        else if (k > n.k) {
            n.r = new Node(k, true);
            n.r.p = n;
            this.addFix(n.r);
        }
    }

    private void addFix(Node z) {
        Node y;
        while (z.p.red) {
            if (z.p == z.p.p.l) {
                y = z.p.p.r;
                if (y.red) {
                    z.p.red = false; // case 1
                    y.red = false; // case 1
                    z.p.p.red = true; // case 1
                    z = z.p.p; // case 1
                }
                else {
                    if (z == z.p.r) {
                        z = z.p; // case 2
                        this.rotateLeft(z); // case 2
                    }
                    z.p.red = false; // case 3
                    z.p.p.red = true; // case 3
                    this.rotateRight(z.p.p); // case 3
                }
            }
            else {
                y = z.p.p.l;
                if (y.red) { // case 1
                    z.p.red = false;
                    y.red = false;
                    z.p.p.red = true;
                    z = z.p.p;
                }
                else {
                    if (z == z.p.l) { // case 2
                        z = z.p;
                        this.rotateRight(z);
                    }
                    // case 3
                    z.p.red = false;
                    z.p.p.red = true;
                    this.rotateLeft(z.p.p);
                }
            }
        }
        this.root.red = false;
    }

    private void rotateLeft(Node x) {
        Node y = x.r;

        x.r = y.l;
        if (y.l != Tree.nil) y.l.p = x;
        y.p = x.p;

        if (x.p == Tree.nil) this.root = y;
        else if (x == x.p.l) x.p.l = y;
        else x.p.r = y;

        y.l = x;
        x.p = y;
    }

    private void rotateRight(Node x) {
        Node y = x.l;

        x.l = y.r;
        if (y.r != Tree.nil) y.r.p = x;
        y.p = x.p;

        if (x.p == Tree.nil) this.root = y;
        else if (x == x.p.l) x.p.l = y;
        else x.p.r = y;

        y.r = x;
        x.p = y;
    }

    public void remove(Node z) {
        Node x, y = z;
        boolean yOriginalRed = y.red;

        if (z.l == Tree.nil) {
            x = z.r;
            this.transplant(z, z.r);
        }
        else if (z.r == Tree.nil) {
            x = z.l;
            this.transplant(z, z.l);
        }
        else {
            y = z.successor();
            yOriginalRed = y.red;
            x = y.r;

            if (y.p == z) x.p = y;
            else {
                this.transplant(y, y.r);
                y.r = z.r;
                y.r.p = y;
            }
            this.transplant(z, y);
            y.l = z.l;
            y.l.p = y;
            y.red = z.red;
        }

        if (!yOriginalRed) this.delFix(x);
    }

    /* Adjusts v's references to match u's:
     * u.p.x = v and v.p = u.p (if v is not Tree.nil).
     * Doesn't touch u.p, u.l and u.r. u is
     * still there as though nothing happened.
     */
    private void transplant(Node u, Node v) {
        if (u.p == Tree.nil) this.root = v;
        else if (u == u.p.l) u.p.l = v;
        else u.p.r = v;
        v.p = u.p;
    }

    private void delFix(Node x) {
        Node w;

        while (x != this.root && !x.red) {
            if (x == x.p.l) {
                w = x.p.r;

                if (w.red) { // case 1
                    w.red = false;
                    x.p.red = true;
                    this.rotateLeft(x.p);
                    w = x.p.r;
                }
                if (!w.l.red && !w.r.red) { // case 2
                    w.red = true;
                    x = x.p;
                }
                else {
                    if (!w.r.red) { // case 3
                        w.l.red = false;
                        w.red = true;
                        this.rotateRight(w);
                        w = x.p.r;
                    }
                    // case 4
                    w.red = x.p.red;
                    x.p.red = false;
                    w.r.red = false;
                    this.rotateLeft(x.p);
                    x = this.root;
                }
            }
            else {
                w = x.p.l;

                if (w.red) { // case 1
                    w.red = false;
                    x.p.red = true;
                    this.rotateRight(x.p);
                    w = x.p.l;
                }
                if (!w.l.red && !w.r.red) { // case 2
                    w.red = true;
                    x = x.p;
                }
                else {
                    if (!w.l.red) { // case 3
                        w.r.red = false;
                        w.red = true;
                        this.rotateLeft(w);
                        w = x.p.l;
                    }
                    // case 4
                    w.red = x.p.red;
                    x.p.red = false;
                    w.l.red = false;
                    this.rotateRight(x.p);
                    x = this.root;
                }
            }
        }
        x.red = false;
    }

    // Remove all nodes in the tree.
    public Tree delete() {
        while (this.root != Tree.nil) {
            this.remove(this.root);
            System.out.println();
            this.graph();
        }
        this.root = null;
        return null;
    }

    public Node min() {
        return this.root.min();
    }

    public Node max() {
        return this.root.max();
    }

    public int size() {
        return this.root.size();
    }

    public int depth() {
        return this.height();
    }

    public int height() {
        return this.root.height();
    }

    public void inorderWalk() {
        this.root.inorderWalk();
    }

    public void graph() {
        System.out.println("digraph RBTree {");
        this.root.graph();
        System.out.println("\tnil [style = filled, fillcolor = black, fontcolor = white];");
        //System.out.println("\tnil -> " + this.root.k + ";");
        System.out.println("}");
    }
}
