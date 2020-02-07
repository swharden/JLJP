/**
 * An example of the use of the Java classes for performing the calculation of
 * the liquid junction potential.
 */

public class Example {

	// Physical costants
	private static final double KT = 1.3806488e-23 * (25.0 + 273.15);
	private static final double e = 1.6e-19;
	private static final double Nav = 6.02e23;
	private static final double epsilon = 8.854187817e-12 * 80.1;

	/**
	 * The example. Make the calculation of a liquid junction between a 9 M solution
	 * of ZnCl2 on the left (position x=0) , and a 3 M solution of KCl on the right
	 * (position x=L). Three ions are thus present. The program writes the
	 * calculated voltage on the standard error, and a table of the three
	 * concentrations and the voltage on the standard out.
	 */

	public static void main(String[] args) throws Exception {

		// First we define the ions, with their name.
		// Suggestion: avoid numbers and symbols like + and -.
		// For ions with multiple charges, use, e.g., FeII and FeIII.

		Ion Zn = new Ion("Zn");
		Ion K = new Ion("K");
		Ion Cl = new Ion("Cl");

		// Then we must set two physical parameters of the ions:
		// charge and mobility

		// The charge is relative to the electron, so, e.g., 1, 2, -1, -2

		// The mobility is v/F, in m/s / N. It's connected to the
		// electrophoretic mobility.

		// For some common ions, the charge and the mobility
		// are already set.
		// For this reason the following lines are not needed,
		// and thus are commented.

		// Zn.setCharge(2); Zn.setMu(52.8e-4/2/Nav/e/e);
		// K.setCharge(1); K.setMu(73.48e-4/Nav/e/e);
		// Cl.setCharge(-1); Cl.setMu(76.31e-4/Nav/e/e);

		// Now we set the parameter "c d ln a / dc".
		// The term cdadc is the derivative of the logarithm of the
		// activity by the concentration, multiplied by the concentration.
		// Both concentration and activity are expressed in M.
		// For small concentrations, this term is 1, and needs not be
		// defined.
		// Functions can be provided, written as strings. The
		// name of the ions is the concentration in M.

		Zn.setCdadc("-1.0+2.0/3.0*(atan((Zn-5.45776)*0.408978)*2.35004+3.0-atan(-5.45776*0.408978)*2.35004)");
		K.setCdadc("1.0");
		Cl.setCdadc("1.0/2.0+1.0/6.0*(atan((Zn-5.45776)*0.408978)*2.35004+3.0-atan(-5.45776*0.408978)*2.35004)");

		// We set the concentration in M at the beginning and end of
		// the liquid junction.
		// The value of Phi is approximately CL-C0.
		Zn.setC0(9.0 * Nav * 1000.0);
		K.setC0(0.0 * Nav * 1000.0);
		Cl.setC0(18.0 * Nav * 1000.0);
		Zn.setCL(0.0);
		K.setCL(3.0 * Nav * 1000.0);
		Cl.setCL(3.0 * Nav * 1000.0);

		// Define the IonSet

		IonSet isss = new IonSet();

		// Put the Ion s in the IonSet. The last two take a
		// particular meaning: they will be the "X" and "Last"
		// (see the documentation for the definition)

		isss.add(Zn);
		isss.add(K);
		isss.add(Cl);

		System.err.println("Voltage: " + isss.calculate(System.out));

	}

}