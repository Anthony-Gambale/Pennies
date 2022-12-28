import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquivalenceClassTest {

    @Test
    public void testAllQuick() {
        testHelper(15, 15, 15);
    }

    @Test
    public void testAllMedium() {
        testHelper(40, 50, 50);
    }

    @Test
    public void testAllDeep() {
        testHelper(90, 90, 90);
    }

    @Test
    public void testAllExtremelyDeep() {
        testHelper(120, 120, 120);
    }

    public void testHelper(int _i, int _j, int _k) {
        for (int i = 1; i < _i; i++) {
            for (int j = 1; j < _j; j++) {
                for (int k = 1; k < _k; k++) {
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
