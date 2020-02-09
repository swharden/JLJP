import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class CalculationTest {

    private static final double mVperV = 1000.0;
    private static final boolean printLog = false;

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

    @Test
    public void test_ljpMath_exampleFromScreenshot() throws Exception {
        /* this test came from screenshot on JLJP website */

        IonSet isss = new IonSet();
        isss.add(new Ion("Zn", 9, 0.0284));
        isss.add(new Ion("K", 0, 3)); // second from last is "X"
        isss.add(new Ion("Cl", 18, 3.0568)); // last becomes "last"

        double ljpVolts = isss.calculate(null);
        double ljpMillivolts = ljpVolts * mVperV;
        if (printLog)
            System.out.println(String.format("LJP = %f mV", ljpMillivolts));

        double expectedMillivolts = -20.823125;
        double decimalsOfPrecision = 5;
        assertEquals(ljpMillivolts, expectedMillivolts, decimalsOfPrecision);
    }

}