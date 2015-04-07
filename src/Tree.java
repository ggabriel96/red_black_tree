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
    public Node root;
    public static Node nil = new Node(0, false);

    public Tree(int key) {
        this.root = new Node(key, false);
    }

    public Node find(int key) {
        return this.root.find(key);
    }

    public void add(int key) {
        Node n = this.find(key);
        if (key < n.key) {
            n.left = new Node(key, true);
            n.left.p = n;
            this.addFix(n.left);
        }
        else if (key > n.key) {
            n.right = new Node(key, true);
            n.right.p = n;
            this.addFix(n.right);
        }
    }

    private void addFix(Node z) {
        Node y;
        while (z.p.red) {
            if (z.p == z.p.p.left) {
                y = z.p.p.right;
                if (y.red) { // case 1: while repeats only if y.red
                    /* if my uncle is red, I change the color
                     * of my parent and uncle to black and
                     * my grandparent's color to red
                     * then, go up 2 levels on the tree
                     */
                    z.p.red = false;
                    y.red = false;
                    z.p.p.red = true;
                    z = z.p.p;
                }
                else { // uncle is black
                    if (z == z.p.right) { // case 2
                        z = z.p;
                        this.rotateLeft(z);
                    }
                    // case 3
                    z.p.red = false;
                    z.p.p.red = true;
                    this.rotateRight(z.p.p);
                }
            }
            else {
                y = z.p.p.left;
                if (y.red) { // case 1
                    y.red = z.p.red = false;
                    z.p.p.red = true;
                    z = z.p.p;
                }
                else {
                    if (z == z.p.left) { // case 2
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
        Node y = x.right;

        x.right = y.left;
        if (y.left != Tree.nil) y.left.p = x;
        y.p = x.p;

        if (x.p == Tree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.left = x;
        x.p = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;

        x.left = y.right;
        if (y.right != Tree.nil) y.right.p = x;
        y.p = x.p;

        if (x.p == Tree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.right = x;
        x.p = y;
    }

    public void remove(int key) {
        Node z = this.find(key);
        Node x, y = z;
        boolean yOriginalRed = y.red;

        if (z.left == Tree.nil) {
            x = z.right;
            this.transplant(z, z.right);
        }
        else if (z.right == Tree.nil) {
            x = z.left;
            this.transplant(z, z.left);
        }
        else {
            y = z.successor();
            yOriginalRed = y.red;
            x = y.right;

            if (y.p == z) x.p = y;
            else {
                this.transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            this.transplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.red = z.red;
        }

        if (!yOriginalRed) this.remFix(x);
    }

    /* Adjusts v's references to match u's:
     * u.p.x = v and v.p = u.p (if v is not Tree.nil).
     * Doesn't touch u.p, u.left and u.right. u is
     * still there as though nothing happened.
     */
    private void transplant(Node u, Node v) {
        if (u.p == Tree.nil) this.root = v;
        else if (u == u.p.left) u.p.left = v;
        else u.p.right = v;
        v.p = u.p;
    }

    private void remFix(Node x) {
        Node w;

        while (x != this.root && !x.red) {
            if (x == x.p.left) {
                w = x.p.right;

                if (w.red) { // case 1
                    w.red = false;
                    x.p.red = true;
                    this.rotateLeft(x.p);
                    w = x.p.right;
                }
                if (!w.left.red && !w.right.red) { // case 2
                    w.red = true;
                    x = x.p;
                }
                else {
                    if (!w.right.red) { // case 3
                        w.left.red = false;
                        w.red = true;
                        this.rotateRight(w);
                        w = x.p.right;
                    }
                    // case 4
                    w.red = x.p.red;
                    x.p.red = false;
                    w.right.red = false;
                    this.rotateLeft(x.p);
                    x = this.root;
                }
            }
            else {
                w = x.p.left;

                if (w.red) { // case 1
                    w.red = false;
                    x.p.red = true;
                    this.rotateRight(x.p);
                    w = x.p.left;
                }
                if (!w.left.red && !w.right.red) { // case 2
                    w.red = true;
                    x = x.p;
                }
                else {
                    if (!w.left.red) { // case 3
                        w.right.red = false;
                        w.red = true;
                        this.rotateLeft(w);
                        w = x.p.left;
                    }
                    // case 4
                    w.red = x.p.red;
                    x.p.red = false;
                    w.left.red = false;
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
            this.remove(this.root.key);
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
        //System.out.println("\tnil -> " + this.root.key + ";");
        System.out.println("}");
    }
}
