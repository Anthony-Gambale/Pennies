import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquivalenceClassTest {

    @Test
    public void testPrev() {
        // prev().next() should be the identity operation
        // millions of cases, should take no longer than a minute
        for (int i = 1; i < 100; i++) {
            for (int j = 1; j < 100; j++) {
                for (int k = 1; k < 100; k++) {
                    testPrevHelper(new int[] {i,j,k});
                    testPrevHelper(new int[] {i,j,k,i,i,j,i,j});
                }
            }
        }
    }

    @Test
    public void testTotal() {
        // total number of pennies in this, prevs and next should all be equal
        // millions of cases, should take no longer than a minute
        for (int i = 1; i < 100; i++) {
            for (int j = 1; j < 100; j++) {
                for (int k = 1; k < 100; k++) {
                    testTotalHelper(new int[] {i,j,k});
                    testTotalHelper(new int[] {i,j,k,i,i,j,i,j});
                }
            }
        }
    }

    public void testPrevHelper(int[] arr) {
        EquivalenceClass x = new EquivalenceClass(arr);
        for (EquivalenceClass y : x.prev()) {
            EquivalenceClass z = y.next();
            assertEquals(z.piles, x.piles, "Broken case: program says\n" + y + "\nis a predecessor class of\n" + x + "\nbut its next() method returns\n" + z);
        }
    }

    public void testTotalHelper(int[] arr) {
        EquivalenceClass x = new EquivalenceClass(arr);
        EquivalenceClass y = x.next();
        if (y != null)
            assertEquals(x.total(), y.total(), "Broken case: program says\n" + y + "\nis a successor of\n" + x + "\nbut they have a different total number of pennies.");
        for (EquivalenceClass z : x.prev()) {
            assertEquals(x.total(), z.total(), "Broken case: program says\n" + z + "\nis a predecessor of\n" + x + "\nbut they have a different total number of pennies.");
        }
    }
}
