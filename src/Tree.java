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
        }
        else if (k > n.k) {
            n.r = new Node(k, true);
            n.r.p = n;
        }
    }

    public void rotateLeft(Node x) {
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

    public void rotateRight(Node x) {
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

    public void remove(Node u) {
        /* This first 'if' treats the case when u has
         * no children *or* has a right child, but not left.
         */
        if (u.l == Tree.nil) this.transplant(u, u.r);
        else if (u.r == Tree.nil) this.transplant(u, u.l);
        else {
            Node v = u.successor();
            /* If u has two children, gotta transplant it
             * with its successor (v). If v is not a child
             * of u, makes v.r be the new child of v.p.
             * Then, v.r receives u's right sub-tree.
             */
            if (v.p != u) {
                this.transplant(v, v.r);
                v.r = u.r;
                v.r.p = v;
            }
            /* Now, v has already taken u's place,
             * except for the last step:
             */
            this.transplant(u, v);
            v.l = u.l;
            v.l.p = v;
            /* transplant u and v so that we set the
             * new parent of v (old u.p). Now, there
             * is not a single reference to u and then
             * it will be freed by the garbage collector
             * (u.l and u.r are intact. That doesn't matter).
             * If v is a child of u (the right one), then
             * only the last step is performed.
             * Important: v.l is Tree.nil, since v is u's successor.
             */
        }
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
        if (v != Tree.nil) v.p = u.p;
    }

    // Remove all nodes in the tree.
    public void delete() {
        while (this.root != Tree.nil) this.remove(this.root);
        this.root = null;
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
        System.out.println("\tnil -> " + this.root.k + ";");
        System.out.println("}");
    }
}
