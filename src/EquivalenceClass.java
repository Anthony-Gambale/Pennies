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
        EquivalenceClass x = new EquivalenceClass(new int[] {333, 333});
        System.out.println(x);
        while ((x = x.next()) != null)
            System.out.println(x);
    }
}
