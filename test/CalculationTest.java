import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class CalculationTest {

    @Test
    public void test_testingPlatform_newListIsEmpty() {
        Collection collection = new ArrayList();
        assertTrue(collection.isEmpty());
    }

    @Test
    public void test_ion_nameLookupWorks() {
        Ion Zn = new Ion("Zn");
        Ion Default = new Ion("lolz");
        assertTrue(Zn.getMu() != Default.getMu());
    }

    @Test
    public void test_ion_nameIsCaseInsensitive() {
        Ion Zn = new Ion("Zn");
        Ion ZnUpper = new Ion("ZN");
        Ion ZnLower = new Ion("zn");
        assertTrue(Zn.getMu() == ZnUpper.getMu());
        assertTrue(Zn.getMu() == ZnLower.getMu());
        assertTrue(Zn.getMu() == ZnLower.getMu());
    }

}