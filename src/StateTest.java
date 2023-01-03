import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    public void testAll() {
        testHelper(1, 45);
    }

    @Test
    public void testAllLarge() {
        testHelper(300, 330);
    }

    public void testHelper(int min, int max) {
        for (int i = min; i < max; i++) {
            for (int j = min; j < max; j++) {
                for (int k = min; k < max; k++) {
                    testPrevHelper(new int[] {i,j,k,i,j,i,j});
                    testTotalHelper(new int[] {i,j,k,i,j,i,j});
                    testDoublePrevHelper(new int[] {i,j,k,i,j,i,j});
                    testNonZeroHelper(new int[] {i,j,k,i,j,i,j});
                }
            }
        }
    }

    public void testNonZeroHelper(int[] arr) {
        State x = new State(arr);
        for (State y : x.prev()) {
            for (int pile : y.piles.keySet()) {
                boolean valid = y.piles.get(pile) > 0;
                assertTrue(valid, "Program says\n" + y
                        + "\nis a predecessor of \n" + x
                        + "\nbut its hashmap contains invalid amounts\n" + y.piles);
            }
        }
        State z = x.next();
        for (int pile : z.piles.keySet()) {
            boolean valid = z.piles.get(pile) > 0;
            assertTrue(valid, "Program says\n" + z
                    + "\nis a successor of \n" + x
                    + "\nbut its hashmap contains invalid amounts\n" + z.piles);
        }
    }

    public void testPrevHelper(int[] arr) {
        State x = new State(arr);
        for (State y : x.prev()) {
            State z = y.next();
            if (z != null)
                assertEquals(z.piles, x.piles, "Broken case: program says\n" + y
                        + "\nis a predecessor class of\n" + x
                        + "\nbut its next() method returns\n" + z);
        }
    }

    public void testTotalHelper(int[] arr) {
        State x = new State(arr);
        State y = x.next();
        if (y != null)
            assertEquals(x.total(), y.total(), "Broken case: program says\n" + y
                    + "\nis a successor of\n" + x
                    + "\nbut they have a different total number of pennies.");
        for (State z : x.prev()) {
            assertEquals(x.total(), z.total(), "Broken case: program says\n" + z
                    + "\nis a predecessor of\n" + x
                    + "\nbut they have a different total number of pennies.");
        }
    }

    public void testDoublePrevHelper(int[] arr) {
        State x = new State(arr);
        for (State y : x.prev()) {
            for (State z : y.prev()) {
                assertEquals(z.total(), x.total(), "Broken case: program says\n" + z
                        + "\nis a predecessor of a predecessor of\n" + x
                        + "\nbut they have a different total number of pennies. The intermediary state was\n" + y);
            }
        }
    }
}
