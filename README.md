# red_black_tree
An implementation of the red-black tree in Java. This code was forked from my repo "binary_search_tree".

**Important:** some of this code is based on Cormen's Introduction to Algorithms and Open Data Structures code and content/info about trees. I'm really thankful for their effort on writing their books and this repository wouldn't have all these methods if it weren't for them.

**Links:**

* [Introduction to Algorithms, by Cormen et al](http://www.mitpress.mit.edu/books/introduction-algorithms)
* [Open Data Structures](http://www.opendatastructures.org)

**Some info:**

* `p`, `left` and `right` are the parent node and left and right children, respectively. `key` is the information that node holds. The boolean `red` indicates if that node is red, obviously haha;

* The `find()` method searches for the node that contains the int passed as argument. If found, returns it. If not, returns the node that would be its parent if the searched item existed at that moment;

* The `add()` method uses `find()` to get to the place where the new node should be added. If a node already has the int passed as argument, it ignores it and doesn't add a thing (*so there are no duplicates*). If the new node is smaller than the current being looked at, it goes to the left; if it's greater, it goes to the right. At the end, calls `addFix()` so that it takes care of the potential violations of the red-black properties (this then calls `rotateLeft()` and `rotateRight()` when necessary);

* The `remove()` method uses `transplant()` (that performs a swap between nodes) and `remFix()` to remove the node that contains the int passed as argument from its tree. `remFix()` is analogous to `addFix()`;

* The `delete()` method calls `remove()` on the root node while it's not `Tree.nil` to completely delete the tree. Sets the root to `null` and also returns `null` so we don't need another line to set our Tree object to `null`;

* The `min()` and `max()` methods return the node that holds the minimum and maximum values of the tree rooted at the object calling it;

* The `predecessor()` and `successor()` methods return the predecessor (`max()` of its left child) and successor (`min()` of the right child) of the object calling it. If the respective child doesn't exist, returns itself;

* The `size()` method returns the quantity of nodes in the (sub-)tree rooted at the object calling it;

* The `depth()` returns the length of the path from the object calling it to the root of the tree. If called from a Tree object, will return the height of the tree, as it doesn't make sense to calculate the depth of the root (and the total depth of the tree is equal to its height);

* The `height()` returns the length of the path from the object calling it to the farthest descendant/leaf;

* And the `inorderWalk()` method prints the key of all nodes in ascending order, one per line;

* Finally, the `graph()` method outputs the tree in the [GraphViz](http://www.graphviz.org/) dot language format. The nodes are colored accordingly and it prints `L` and `R` for the left and right child.
