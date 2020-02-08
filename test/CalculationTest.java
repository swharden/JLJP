import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class CalculationTest {

    private static final double mMperM = 1000.0;
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
    public void test_ljpMath_sampleCalculation1() throws Exception {

        // Define ions and conditions (charge and cdadc come from the table)
        Ion Zn = new Ion("Zn");
        Zn.setC0(9.0 * Ion.Nav * mMperM);
        Zn.setCL(0.0284 * Ion.Nav * mMperM);

        Ion K = new Ion("K");
        K.setC0(0.0 * Ion.Nav * mMperM);
        K.setCL(3.0 * Ion.Nav * mMperM);

        Ion Cl = new Ion("Cl");
        Cl.setC0(18.0 * Ion.Nav * mMperM);
        Cl.setCL(3.0568 * Ion.Nav * mMperM);

        // Load ions in order (the last 2 become "X" and "Last")
        IonSet isss = new IonSet();
        isss.add(Zn);
        isss.add(K);
        isss.add(Cl);

        // calculate LJP
        double ljpVolts = isss.calculate(null);
        double ljpMillivolts = ljpVolts * mVperV;
        if (printLog)
            System.out.println(String.format("LJP = %f mV", ljpMillivolts));

        // verify LJP
        double expectedMillivolts = -20.823125;
        double decimalsOfPrecision = 5;
        assertEquals(ljpMillivolts, expectedMillivolts, decimalsOfPrecision);
    }

}