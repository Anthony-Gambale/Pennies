import java.util.HashSet;
import java.util.Set;

public class State {

    int[] piles;
    int total;

    public State(int total, int[] initial) {
        this.total = total;
        if (initial.length == total) {
            this.piles = initial;
        } else {
            this.piles = new int[total];
            System.arraycopy(initial, 0, piles, 0, initial.length);
        }
    }

    public State next() {
        int[] rt = new int[this.total];
        int i = 0;
        int j = 0;
        boolean oneCycle = true;
        while (i < this.total && this.piles[i] > 0) {
            if (this.piles[i] > 1) {
                rt[j] = this.piles[i] - 1;
                if (rt[j] != this.piles[j]) oneCycle = false;
                j += 1;
            }
            i += 1;
        }
        rt[j] = i;
        if (rt[j] != this.piles[j]) oneCycle = false;
        if (oneCycle && rt[j] != this.total) return null;
        else return new State(this.total, rt);
    }

    public Set<State> prev() {
        // TODO: compute the set of states whose next() operation return this state
        return new HashSet<>();
    }

    public String toString() {
        StringBuilder rt = new StringBuilder();
        int i = 0;
        while (i < this.total && this.piles[i] > 0) {
            rt.append(this.piles[i]);
            rt.append(" ");
            i += 1;
        }
        return rt.toString();
    }

    public static void main(String[] args) {
        State x = new State(666, new int[] {333, 333});
        System.out.println(x);
        while ((x = x.next()) != null)
            System.out.println(x);
    }
}
