import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EquivalenceClass {

    // size of pile : number of piles of this size
    Map<Integer, Integer> piles;

    public EquivalenceClass(int[] initial) {
        this.piles = new HashMap<>();
        for (int pile : initial) {
            if (this.piles.containsKey(pile))
                this.piles.put(pile, this.piles.get(pile) + 1); // increment
            else
                this.piles.put(pile, 1); // init
        }
    }

    public EquivalenceClass(Map<Integer, Integer> initial) {
        this.piles = initial;
    }

    public EquivalenceClass next() {
        Map<Integer, Integer> rtn = new HashMap<>();
        int npiles = 0;

        for (int pile : this.piles.keySet()) {
            if (pile > 1)
                rtn.put(pile - 1, this.piles.get(pile));
            npiles += this.piles.get(pile);
        }

        if (rtn.containsKey(npiles))
            rtn.put(npiles, rtn.get(npiles) + 1);
        else
            rtn.put(npiles, 1);

        if (this.piles.equals(rtn)) return null;
        return new EquivalenceClass(rtn);
    }

    public int total() {
        int rt = 0;
        for (int pile : this.piles.keySet())
            rt += this.piles.get(pile) * pile;
        return rt;
    }

    public Set<EquivalenceClass> prev() {
        Set<EquivalenceClass> rt = new HashSet<>();
        int this_total = this.total();

        for (int artificial_size : this.piles.keySet()) {
            // test each pile size for the artificial pile
            Map<Integer, Integer> curr = new HashMap<>();
            // add all piles + 1, except the artificial one
            // also count total number of pennies so far
            int total = 0;
            for (int add_size : this.piles.keySet()) {
                int amt = this.piles.get(add_size);
                if (add_size == artificial_size) {
                    if (amt > 1) {
                        curr.put(add_size + 1, amt - 1);
                        total += (add_size + 1) * (amt - 1);
                    }
                } else {
                    curr.put(add_size + 1, amt);
                    total += (add_size + 1) * amt;
                }
            }
            // check total
            if (total <= this_total) {
                // meet the difference with size 1 piles
                if (total < this_total)
                    curr.put(1, this_total - total);
                rt.add(new EquivalenceClass(curr));
            }
        }
        return rt;
    }

    public String toString() {
        StringBuilder rt = new StringBuilder();
        rt.append("[ ");
        for (int pile : this.piles.keySet()) {
            for (int i = 0; i < this.piles.get(pile); i++) {
                rt.append(pile);
                rt.append(" ");
            }
        }
        rt.append("]");
        return rt.toString();
    }

    public void printPredecessorTree(int level) {
        this.printPredecessorTreeHelper(level, 0);
    }

    public void printPredecessorTreeHelper(int level, int curr_level) {
        if (curr_level == level) return;
        String whitespace = new String(new char[curr_level]).replace("\0", "|   ");
        System.out.println(whitespace + this);
        for (EquivalenceClass x : this.prev())
            if (!x.piles.equals(this.piles))
                x.printPredecessorTreeHelper(level, curr_level + 1);
    }

    public static void main(String[] args) {
        EquivalenceClass x = new EquivalenceClass(new int[] {5,5});

        System.out.println(x);
        while ((x = x.next()) != null)
            System.out.println(x);

        System.out.println();
        x = new EquivalenceClass(new int[] {1,2,3,4});
        x.printPredecessorTree(100);
    }
}
