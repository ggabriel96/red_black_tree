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

class RBTree {
    public RBNode root;
    public static RBNode nil = new RBNode(0, false);

    public RBTree() {
        this.root = RBTree.nil;
    }

    public RBTree(int key) {
        this.root = new RBNode(key, false);
    }

    public RBNode find(int key) {
        return this.root.find(key);
    }

    public RBTree find50(int from) {
        Counter c = new Counter(0);
        RBTree fifty = new RBTree();

        this.root.find50(c, from, fifty);

        return fifty;
    }

    public void add(int key) {
        if (this.root == RBTree.nil) {
            this.root = new RBNode(key, false);
        }
        else {
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
        if (z.key == key) {
            RBNode x, y = z;
            // we gotta backup y's original color, because if
            // it was black, we might have violated a property
            // of the red-black tree by moving it around
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
                // we'll replace z by its successor
                y = z.successor();
                yOriginalRed = y.red; // same here about y's color
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
            // if x is the left child of its parent, do this.
            // the else procedure is simetrically the same
            if (x == x.p.left) {
                w = x.p.right;

                // case 1: x's sibling is red. Switch colors and
                // rotate to change this case to be either case 2,
                // 3 or 4 (w is black)
                if (w.red) {
                    w.red = false;
                    x.p.red = true;
                    this.rotateLeft(x.p);
                    w = x.p.right;
                }

                // case 2: w and both of its children are black
                if (!w.left.red && !w.right.red) {
                    w.red = true;
                    x = x.p;
                }
                else {
                    // case 3: w's left child is red (or right
                    // child is black). Switch colors and rotate
                    // to become case 4
                    if (!w.right.red) {
                        w.left.red = false;
                        w.red = true;
                        this.rotateRight(w);
                        w = x.p.right;
                    }
                    // case 4: w's right child is red
                    w.red = x.p.red;
                    x.p.red = false;
                    w.right.red = false;
                    this.rotateLeft(x.p);
                    x = this.root;
                }
            }
            else {
                w = x.p.left;

                // case 1
                if (w.red) {
                    w.red = false;
                    x.p.red = true;
                    this.rotateRight(x.p);
                    w = x.p.left;
                }
                // case 2
                if (!w.left.red && !w.right.red) {
                    w.red = true;
                    x = x.p;
                }
                else {
                    // case 3
                    if (!w.left.red) {
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
