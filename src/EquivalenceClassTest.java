import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquivalenceClassTest {

    @Test
    public void testAllQuick() {
        // prev().next() should be the identity operation
        // millions of cases, should take no longer than a minute
        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 15; j++) {
                for (int k = 1; k < 15; k++) {
                    testPrevHelper(new int[] {i,j,k,j,j,i});

                    testTotalHelper(new int[] {i,j,k,j,j,i});

                    testDoublePrevHelper(new int[] {i,j,k,j,j,i});
                }
            }
        }
    }

    @Test
    public void testAllMedium() {
        // prev().next() should be the identity operation
        // millions of cases, should take no longer than a minute
        for (int i = 1; i < 50; i++) {
            for (int j = 1; j < 50; j++) {
                for (int k = 1; k < 50; k++) {
                    testPrevHelper(new int[] {i,j,k,k,j,j});

                    testTotalHelper(new int[] {i,j,k,k,j,j});

                    testDoublePrevHelper(new int[] {i,j,k,k,j,j});
                }
            }
        }
    }

    @Test
    public void testAllDeep() {
        // prev().next() should be the identity operation
        // millions of cases, should take no longer than a minute
        for (int i = 1; i < 90; i++) {
            for (int j = 1; j < 90; j++) {
                for (int k = 1; k < 90; k++) {
                    testPrevHelper(new int[] {i,j,k,i,j,i,j});

                    testTotalHelper(new int[] {i,j,k,i,j,i,j});

                    testDoublePrevHelper(new int[] {i,j,k,i,j,i,j});
                }
            }
        }
    }

    public void testPrevHelper(int[] arr) {
        EquivalenceClass x = new EquivalenceClass(arr);
        for (EquivalenceClass y : x.prev()) {
            EquivalenceClass z = y.next();
            if (z != null)
                assertEquals(z.piles, x.piles, "Broken case: program says\n" + y
                        + "\nis a predecessor class of\n" + x
                        + "\nbut its next() method returns\n" + z);
        }
    }

    public void testTotalHelper(int[] arr) {
        EquivalenceClass x = new EquivalenceClass(arr);
        EquivalenceClass y = x.next();
        if (y != null)
            assertEquals(x.total(), y.total(), "Broken case: program says\n" + y
                    + "\nis a successor of\n" + x
                    + "\nbut they have a different total number of pennies.");
        for (EquivalenceClass z : x.prev()) {
            assertEquals(x.total(), z.total(), "Broken case: program says\n" + z
                    + "\nis a predecessor of\n" + x
                    + "\nbut they have a different total number of pennies.");
        }
    }

    public void testDoublePrevHelper(int[] arr) {
        EquivalenceClass x = new EquivalenceClass(arr);
        for (EquivalenceClass y : x.prev()) {
            for (EquivalenceClass z : y.prev()) {
                assertEquals(z.total(), x.total(), "Broken case: program says\n" + z
                        + "\nis a predecessor of a predecessor of\n" + x
                        + "\nbut they have a different total number of pennies. The intermediary state was\n" + y);
            }
        }
    }
}
