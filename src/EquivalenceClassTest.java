import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquivalenceClassTest {

    @Test
    public void testPrev() {
        // prev().next() should be the identity operation
        // 3 million cases, should take no longer than a minute
        for (int i = 1; i < 150; i++) {
            for (int j = 1; j < 150; j++) {
                for (int k = 1; k < 150; k++) {
                    EquivalenceClass x = new EquivalenceClass(new int[] {i,j,k,i,i,j,i,j});
                    for (EquivalenceClass y : x.prev()) {
                        EquivalenceClass z = y.next();
                        assertEquals(z.piles, x.piles, "Broken case: program says\n" + y + "\nis a predecessor class of\n" + x + "\nbut its next() method returns\n" + z);
                    }
                }
            }
        }
    }
}
