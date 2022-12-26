

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
        while (this.piles[i] > 0) {
            if (this.piles[i] > 1) {
                rt[j] = this.piles[i] - 1;
                j += 1;
            }
            i += 1;
        }
        rt[j] = i;
        return new State(this.total, rt);
    }

    public String toString() {
        StringBuilder rt = new StringBuilder();
        int i = 0;
        while (this.piles[i] > 0) {
            rt.append(this.piles[i]);
            rt.append(" ");
            i += 1;
        }
        return rt.toString();
    }

    public static void main(String[] args) {
        State x = new State(10, new int[] {1,2,3,4});
        System.out.println(x);
        System.out.println(x.next());
    }
}
