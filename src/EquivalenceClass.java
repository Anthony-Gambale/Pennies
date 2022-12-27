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

    public boolean equals(EquivalenceClass other) {
        return this.piles.equals(other.piles);
    }

    public EquivalenceClass next() {
        Map<Integer, Integer> rti = new HashMap<>();
        int npiles = 0;

        for (int pile : this.piles.keySet()) {
            if (pile > 1)
                rti.put(pile - 1, this.piles.get(pile));
            npiles += this.piles.get(pile);
        }

        if (rti.containsKey(npiles))
            rti.put(npiles, rti.get(npiles) + 1);
        else
            rti.put(npiles, 1);

        EquivalenceClass rtn = new EquivalenceClass(rti);
        if (this.equals(rtn)) return null;
        return rtn;
    }

    public Set<EquivalenceClass> prev() {
        // TODO: compute the set of equivalence classes whose next() operation return this equivalence class
        return new HashSet<>();
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
        EquivalenceClass x = new EquivalenceClass(new int[] {1,4,1});
        System.out.println(x);
        while ((x = x.next()) != null)
            System.out.println(x);
    }
}
