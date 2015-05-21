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

class RBTree {
    public RBNode root;
    public static RBNode nil = new RBNode(0, false);

    public RBTree(int key) {
        this.root = new RBNode(key, false);
    }

    public RBNode find(int key) {
        return this.root.find(key);
    }

    public void add(int key) {
        RBNode n = this.find(key);
        if (key < n.key) {
            n.left = new RBNode(key, true);
            n.left.p = n;
            this.addFix(n.left);
        }
        else if (key > n.key) {
            n.right = new RBNode(key, true);
            n.right.p = n;
            this.addFix(n.right);
        }
    }

    private void addFix(RBNode z) {
        RBNode y;
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

    private void rotateLeft(RBNode x) {
        RBNode y = x.right;

        x.right = y.left;
        if (y.left != RBTree.nil) y.left.p = x;
        y.p = x.p;

        if (x.p == RBTree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.left = x;
        x.p = y;
    }

    private void rotateRight(RBNode x) {
        RBNode y = x.left;

        x.left = y.right;
        if (y.right != RBTree.nil) y.right.p = x;
        y.p = x.p;

        if (x.p == RBTree.nil) this.root = y;
        else if (x == x.p.left) x.p.left = y;
        else x.p.right = y;

        y.right = x;
        x.p = y;
    }

    public void remove(int key) {
        RBNode z = this.find(key);
        RBNode x, y = z;
        boolean yOriginalRed = y.red;

        if (z.left == RBTree.nil) {
            x = z.right;
            this.transplant(z, z.right);
        }
        else if (z.right == RBTree.nil) {
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
     * u.p.x = v and v.p = u.p (if v is not RBTree.nil).
     * Doesn't touch u.p, u.left and u.right. u is
     * still there as though nothing happened.
     */
    private void transplant(RBNode u, RBNode v) {
        if (u.p == RBTree.nil) this.root = v;
        else if (u == u.p.left) u.p.left = v;
        else u.p.right = v;
        v.p = u.p;
    }

    private void remFix(RBNode x) {
        RBNode w;

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
    public RBTree delete() {
        while (this.root != RBTree.nil) {
            this.remove(this.root.key);
        }
        this.root = null;
        return null;
    }

    public RBNode min() {
        return this.root.min();
    }

    public RBNode max() {
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
