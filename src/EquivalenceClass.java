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

    public Set<EquivalenceClass> prev() {
        Set<EquivalenceClass> rt = new HashSet<>();
        int this_total = 0;
        for (int pile : this.piles.keySet())
            this_total += this.piles.get(pile) * pile;

        for (int pile : this.piles.keySet()) {
            // test each pile size for the artificial pile
            Map<Integer, Integer> curr = new HashMap<>();
            // add all piles + 1, except the artificial one
            // also count total number of pennies so far
            int total = 0;
            for (int add_pile : this.piles.keySet()) {
                int amt = this.piles.get(add_pile);
                if (add_pile == pile) {
                    curr.put(add_pile + 1, amt - 1);
                    total += (add_pile + 1) * (amt - 1);
                }
                else {
                    curr.put(add_pile + 1, amt);
                    total += (add_pile + 1) * amt;
                }
            }
            // check total
            if (total <= this_total) {
                curr.put(1, this_total - total); // meet the difference with size 1 piles
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

    public static void main(String[] args) {
        EquivalenceClass x = new EquivalenceClass(new int[] {1, 5});
        System.out.println(x);
        while ((x = x.next()) != null)
            System.out.println(x);
    }
}
