import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculationTest {

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

		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Zn", 9, 0.0284));
		ionSet.add(new Ion("K", 0, 3)); // second from last is "X"
		ionSet.add(new Ion("Cl", 18, 3.0568)); // last becomes "last"

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = -20.82;
		double allowableDifference_mV = .01;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
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

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = -16.65;
		double allowableDifference_mV = .5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_001() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 50 mM NaCl : 50 mM KCl
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Na", 50, 0));
		ionSet.add(new Ion("K", 0, 50));
		ionSet.add(new Ion("Cl", 50, 50));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = -4.3;
		double allowableDifference_mV = .5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_002() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 150 mM NaCl : 150 mM KCl
		// Na (150), Cl (150) : K (150) Cl (150)
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Na", 150, 0));
		ionSet.add(new Ion("K", 0, 150));
		ionSet.add(new Ion("Cl", 150, 150));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = -4.3;
		double allowableDifference_mV = .5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_003() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 50 NaCl : 50 CsCl
		// Na (50), Cl (50) : Cs (50) Cl (50)
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Na", 50, 0));
		ionSet.add(new Ion("Cs", 0, 50));
		ionSet.add(new Ion("Cl", 50, 50));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = -4.9;
		double allowableDifference_mV = 1.5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_004() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 100 NaCl : 100 MgCl2
		// Na (100), Cl (100) : Mg (100) Cl (100)
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Na", 100, 0));
		ionSet.add(new Ion("Mg", 0, 100));
		ionSet.add(new Ion("Cl", 100, 200));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = +10.0;
		double allowableDifference_mV = 1.5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_005() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 100 CaCl2 : 100 MgCl2
		// Ca (100), Cl (200) : Mg (100) Cl (200)
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Ca", 100, 0));
		ionSet.add(new Ion("Mg", 0, 100));
		ionSet.add(new Ion("Cl", 200, 200));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = +0.6;
		double allowableDifference_mV = 1.5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_006() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 100 KCl + 2 CaCl2 : 100 LiCl + 2 CaCl2
		// K (100), Cl (104), Ca (2) : Li (100), Cl (104), Ca (2)
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Ca", 2, 2));
		ionSet.add(new Ion("K", 100, 0));
		ionSet.add(new Ion("Li", 0, 100));
		ionSet.add(new Ion("Cl", 104, 104));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = +6.4;
		double allowableDifference_mV = 1.5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JLJPvsNgAndBarry_007() throws Exception {
		/* Measured LJP for this test came from Ng and Barry (1994) Table 2 */

		// 50 CaCl2 + 50 MgCl2 : 100 LiCl
		// Ca (50), Cl (200), Mg (50) : Li (100), Cl (100)
		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Ca", 50, 0));
		ionSet.add(new Ion("Cl", 200, 100));
		ionSet.add(new Ion("Mg", 50, 0));
		ionSet.add(new Ion("Li", 0, 100));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = -8.2;
		double allowableDifference_mV = 1.5;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);
	}

	@Test
	public void test_JPWin_1() throws Exception {
		// ion set shown in JPCalcWin manual (page 7)
		// https://tinyurl.com/wk7otn7

		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Na", 10, 145));
		ionSet.add(new Ion("Cl", 10, 145));
		ionSet.add(new Ion("Cs", 135, 0));
		ionSet.add(new Ion("F", 135, 0));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = +8.74;
		double allowableDifference_mV = 1;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);

	}

	@Test
	public void test_JPWin_2() throws Exception {
		// ion set shown in JPCalcWin manual (page 10)
		// https://tinyurl.com/wk7otn7

		IonSet ionSet = new IonSet();
		ionSet.add(new Ion("Cs", 145, 0));
		ionSet.add(new Ion("Na", 0, 145));
		ionSet.add(new Ion("F", 125, 0));
		ionSet.add(new Ion("Cl", 20, 145));

		double calculated_mV = ionSet.calculate(null) * 1000;
		double expected_mV = +8.71;
		double allowableDifference_mV = 1;
		assertEquals(calculated_mV, expected_mV, allowableDifference_mV);

	}
}