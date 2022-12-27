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

    // TODO: return null if the current equivalence class is a one-cycle
    public EquivalenceClass next() {
        Map<Integer, Integer> rt = new HashMap<>();
        int npiles = 0;
        for (int pile : this.piles.keySet()) {
            if (pile > 1) {
                rt.put(pile - 1, this.piles.get(pile));
            }
            npiles += this.piles.get(pile);
        }
        if (rt.containsKey(npiles))
            rt.put(npiles, rt.get(npiles) + 1);
        else
            rt.put(npiles, 1);
        return new EquivalenceClass(rt);
    }

    public Set<EquivalenceClass> prev() {
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
        EquivalenceClass x = new EquivalenceClass(new int[] {1,1,1,1,1,1});
        System.out.println(x);
        for (int i = 0; i < 15; i++) System.out.println(x = x.next());
    }
}
