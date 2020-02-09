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

        double ljp_V = isss.calculate(null);
        double ljp_mV = ljp_V * mVperV;
        if (printLog)
            System.out.println(String.format("LJP = %f mV", ljp_mV));

        double expected_mV = -20.82;
        double allowableDifference_mV = .01;
        assertEquals(ljp_mV, expected_mV, allowableDifference_mV);
    }

    @Test
    public void test_ljpMath_exampleFromSourceCode() throws Exception {
        /* this test came from code and comments in Example.java */

        // create ions
        Ion Zn = new Ion("Zn");
        Ion K = new Ion("K");
        Ion Cl = new Ion("Cl");

        // charges are set automatically for most ions but can be set manually
        Zn.setCharge(2);
        K.setCharge(1);
        Cl.setCharge(-1);

        // mus are set automatically for most ions but can be set manually
        Zn.setMu(52.8e-4 / 2 / Ion.Nav / Ion.e / Ion.e);
        Cl.setMu(76.31e-4 / Ion.Nav / Ion.e / Ion.e);
        K.setMu(73.48e-4 / Ion.Nav / Ion.e / Ion.e);

        // Derivative of log of activity by concentration times concentration
        // For small concentrations, this term is 1, and needs not be defined
        Zn.setCdadc("-1.0+2.0/3.0*(atan((Zn-5.45776)*0.408978)*2.35004+3.0-atan(-5.45776*0.408978)*2.35004)");
        K.setCdadc("1.0");
        Cl.setCdadc("1.0/2.0+1.0/6.0*(atan((Zn-5.45776)*0.408978)*2.35004+3.0-atan(-5.45776*0.408978)*2.35004)");

        // use really large concentrations so cdadc becomes relevant
        Zn.setC0(9.0 * Ion.Nav * 1000.0);
        K.setC0(0.0 * Ion.Nav * 1000.0);
        Cl.setC0(18.0 * Ion.Nav * 1000.0);

        Zn.setCL(0.0);
        K.setCL(3.0 * Ion.Nav * 1000.0);
        Cl.setCL(3.0 * Ion.Nav * 1000.0);

        IonSet ionSet = new IonSet();
        ionSet.add(Zn);
        ionSet.add(K); // second from last is "X"
        ionSet.add(Cl); // last becomes "last"

        double ljp_V = ionSet.calculate(null);
        double ljp_mV = ljp_V * mVperV;
        if (printLog) {
            System.out.println(ionSet.getDescription());
            System.out.println(String.format("LJP = %f mV", ljp_mV));
        }

        double expected_mV = -16.65;
        double allowableDifference_mV = 1;
        assertEquals(ljp_mV, expected_mV, allowableDifference_mV);
    }

    // @Test // this is very slow to run
    public void test_ljpMath_variance() throws Exception {

        // repeatedly calculate LJP from the screenshot values
        int repetitionCount = 10000;
        for (int i = 0; i < repetitionCount; i++) {

            IonSet isss = new IonSet();
            isss.add(new Ion("Zn", 9, 0.0284));
            isss.add(new Ion("K", 0, 3));
            isss.add(new Ion("Cl", 18, 3.0568));

            double ljp_V = isss.calculate(null);
            double ljp_mV = ljp_V * mVperV;
            System.out.println(ljp_mV);
        }

    }
}