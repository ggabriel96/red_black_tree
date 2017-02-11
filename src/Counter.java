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

class Counter {
    private Integer i;

    public Counter(int i) {
        this.i = i;
    }

    public String toString() {
        return this.i.toString();
    }

    public void setValue(int i) {
        this.i = i;
    }
    public int getValue() {
        return this.i;
    }

    public void increment() {
        this.i++;
    }
    public void decrement() {
        this.i--;
    }
}
