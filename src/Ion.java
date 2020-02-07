/**
 * An Ion. You must have an instance of this class for every ion type you have
 * in the solutions.
 */

public class Ion {

	private static final double KT = 1.3806488e-23 * (25.0 + 273.15);
	private static final double e = 1.6e-19;
	private static final double Nav = 6.02e23;
	private static final double epsilon = 8.854187817e-12 * 80.1;

	private String name;
	private int charge;
	private double mu, phi, c0, cL;
	private String cdadc;

	/**
	 * Create an Ion with the given name. Please write the name as "Na", "K", "Cl"
	 * and so on. Avoid numbers and symbols like "+" and "-". For some common ions,
	 * e.g. "Na" or "K", the physical parameters charge and mobility are
	 * automatically set.
	 * 
	 * @param n the name.
	 */
	public Ion(String n) {
		name = n;

		charge = 1;
		mu = 73.48e-4 / Nav / e / e;

		if (name.equals("Ag")) {
			charge = +1;
			mu = 4.01656e+11;
		}
		if (name.equals("Al")) {
			charge = +3;
			mu = 1.31939e+11;
		}
		if (name.equals("Au(CN)2")) {
			charge = -1;
			mu = 3.24439e+11;
		}
		if (name.equals("Au(CN)4")) {
			charge = -1;
			mu = 2.33596e+11;
		}
		if (name.equals("Ba")) {
			charge = +2;
			mu = 2.06343e+11;
		}
		if (name.equals("B(C6H5)4")) {
			charge = -1;
			mu = 1.36265e+11;
		}
		if (name.equals("Be")) {
			charge = +2;
			mu = 1.45998e+11;
		}
		if (name.equals("Br3")) {
			charge = -1;
			mu = 2.79018e+11;
		}
		if (name.equals("Br")) {
			charge = -1;
			mu = 5.06774e+11;
		}
		if (name.equals("BrO3")) {
			charge = -1;
			mu = 3.61425e+11;
		}
		if (name.equals("Ca")) {
			charge = +2;
			mu = 1.92944e+11;
		}
		if (name.equals("Cd")) {
			charge = +2;
			mu = 1.75197e+11;
		}
		if (name.equals("Ce")) {
			charge = +3;
			mu = 1.50972e+11;
		}
		if (name.equals("Cl")) {
			charge = -1;
			mu = 4.95159e+11;
		}
		if (name.equals("ClO2")) {
			charge = -1;
			mu = 3.37417e+11;
		}
		if (name.equals("ClO3")) {
			charge = -1;
			mu = 4.19176e+11;
		}
		if (name.equals("ClO4")) {
			charge = -1;
			mu = 4.36695e+11;
		}
		if (name.equals("CN")) {
			charge = -1;
			mu = 5.06125e+11;
		}
		if (name.equals("CNO")) {
			charge = -1;
			mu = 4.19176e+11;
		}
		if (name.equals("CO3")) {
			charge = -2;
			mu = 2.24836e+11;
		}
		if (name.equals("Co")) {
			charge = +2;
			mu = 1.78442e+11;
		}
		if (name.equals("[Co(CN)6]")) {
			charge = -3;
			mu = 2.13914e+11;
		}
		if (name.equals("[Co(NH3)6]")) {
			charge = +3;
			mu = 2.20402e+11;
		}
		if (name.equals("Cr")) {
			charge = +3;
			mu = 1.44916e+11;
		}
		if (name.equals("CrO4")) {
			charge = -2;
			mu = 2.75773e+11;
		}
		if (name.equals("Cs")) {
			charge = +1;
			mu = 5.00934e+11;
		}
		if (name.equals("Cu")) {
			charge = +2;
			mu = 1.739e+11;
		}
		if (name.equals("D")) {
			charge = +1;
			mu = 1.62155e+12;
		}
		if (name.equals("Dy")) {
			charge = +3;
			mu = 1.41888e+11;
		}
		if (name.equals("Er")) {
			charge = +3;
			mu = 1.42537e+11;
		}
		if (name.equals("Eu")) {
			charge = +3;
			mu = 1.46647e+11;
		}
		if (name.equals("F")) {
			charge = -1;
			mu = 3.59479e+11;
		}
		if (name.equals("FeII")) {
			charge = +2;
			mu = 1.75197e+11;
		}
		if (name.equals("FeIII")) {
			charge = +3;
			mu = 1.47079e+11;
		}
		if (name.equals("[Fe(CN)6]III")) {
			charge = -3;
			mu = 2.1824e+11;
		}
		if (name.equals("[Fe(CN)6]IIII")) {
			charge = -4;
			mu = 1.79091e+11;
		}
		if (name.equals("Gd")) {
			charge = +3;
			mu = 1.45565e+11;
		}
		if (name.equals("H2AsO4")) {
			charge = -1;
			mu = 2.20619e+11;
		}
		if (name.equals("H2PO2")) {
			charge = -1;
			mu = 2.98484e+11;
		}
		if (name.equals("H2PO4")) {
			charge = -1;
			mu = 2.33596e+11;
		}
		if (name.equals("H2SbO4")) {
			charge = -1;
			mu = 2.01152e+11;
		}
		if (name.equals("H")) {
			charge = +1;
			mu = 2.2688e+12;
		}
		if (name.equals("HCO3")) {
			charge = -1;
			mu = 2.88751e+11;
		}
		if (name.equals("HF2")) {
			charge = -1;
			mu = 4.86659e+11;
		}
		if (name.equals("Hg")) {
			charge = +2;
			mu = 2.22565e+11;
		}
		if (name.equals("Ho")) {
			charge = +3;
			mu = 1.43402e+11;
		}
		if (name.equals("HPO4")) {
			charge = -2;
			mu = 1.8493e+11;
		}
		if (name.equals("HS")) {
			charge = -1;
			mu = 4.21771e+11;
		}
		if (name.equals("HSO3")) {
			charge = -1;
			mu = 3.7635e+11;
		}
		if (name.equals("HSO4")) {
			charge = -1;
			mu = 3.37417e+11;
		}
		if (name.equals("I")) {
			charge = -1;
			mu = 4.98339e+11;
		}
		if (name.equals("IO3")) {
			charge = -1;
			mu = 2.62796e+11;
		}
		if (name.equals("IO4")) {
			charge = -1;
			mu = 3.53639e+11;
		}
		if (name.equals("K")) {
			charge = +1;
			mu = 4.76796e+11;
		}
		if (name.equals("La")) {
			charge = +3;
			mu = 1.50756e+11;
		}
		if (name.equals("Li")) {
			charge = +1;
			mu = 2.50857e+11;
		}
		if (name.equals("Mg")) {
			charge = +2;
			mu = 1.71953e+11;
		}
		if (name.equals("Mn")) {
			charge = +2;
			mu = 1.73575e+11;
		}
		if (name.equals("MnO4")) {
			charge = -1;
			mu = 3.97763e+11;
		}
		if (name.equals("MoO4")) {
			charge = -2;
			mu = 2.41707e+11;
		}
		if (name.equals("N2H5")) {
			charge = +1;
			mu = 3.82838e+11;
		}
		if (name.equals("N3")) {
			charge = -1;
			mu = 4.47726e+11;
		}
		if (name.equals("Na")) {
			charge = +1;
			mu = 3.24958e+11;
		}
		if (name.equals("N(CN)2")) {
			charge = -1;
			mu = 3.53639e+11;
		}
		if (name.equals("Nd")) {
			charge = +3;
			mu = 1.50107e+11;
		}
		if (name.equals("NH2SO3")) {
			charge = -1;
			mu = 3.13408e+11;
		}
		if (name.equals("NH4")) {
			charge = +1;
			mu = 4.76926e+11;
		}
		if (name.equals("Ni")) {
			charge = +2;
			mu = 1.60922e+11;
		}
		if (name.equals("NO2")) {
			charge = -1;
			mu = 4.65895e+11;
		}
		if (name.equals("NO3")) {
			charge = -1;
			mu = 4.63429e+11;
		}
		if (name.equals("OCN")) {
			charge = -1;
			mu = 4.19176e+11;
		}
		if (name.equals("OD")) {
			charge = -1;
			mu = 7.72166e+11;
		}
		if (name.equals("OH")) {
			charge = -1;
			mu = 1.28478e+12;
		}
		if (name.equals("Pb")) {
			charge = +2;
			mu = 2.30352e+11;
		}
		if (name.equals("PF6")) {
			charge = -1;
			mu = 3.69212e+11;
		}
		if (name.equals("PO3F")) {
			charge = -2;
			mu = 2.0537e+11;
		}
		if (name.equals("PO4")) {
			charge = -3;
			mu = 2.0072e+11;
		}
		if (name.equals("Pr")) {
			charge = +3;
			mu = 1.50324e+11;
		}
		if (name.equals("Ra")) {
			charge = +2;
			mu = 2.16725e+11;
		}
		if (name.equals("Rb")) {
			charge = +1;
			mu = 5.04828e+11;
		}
		if (name.equals("ReO4")) {
			charge = -1;
			mu = 3.56234e+11;
		}
		if (name.equals("Sb(OH)6")) {
			charge = -1;
			mu = 2.06992e+11;
		}
		if (name.equals("Sc")) {
			charge = +3;
			mu = 1.39942e+11;
		}
		if (name.equals("SCN")) {
			charge = -1;
			mu = 4.2826e+11;
		}
		if (name.equals("SeCN")) {
			charge = -1;
			mu = 4.19825e+11;
		}
		if (name.equals("SeO4")) {
			charge = -2;
			mu = 2.45601e+11;
		}
		if (name.equals("Sm")) {
			charge = +3;
			mu = 1.48161e+11;
		}
		if (name.equals("SO3")) {
			charge = -2;
			mu = 2.33596e+11;
		}
		if (name.equals("SO4")) {
			charge = -2;
			mu = 2.59551e+11;
		}
		if (name.equals("Sr")) {
			charge = +2;
			mu = 1.92717e+11;
		}
		if (name.equals("Tl")) {
			charge = +1;
			mu = 4.84712e+11;
		}
		if (name.equals("Tm")) {
			charge = +3;
			mu = 1.41456e+11;
		}
		if (name.equals("UO2")) {
			charge = +2;
			mu = 1.03821e+11;
		}
		if (name.equals("WO4")) {
			charge = -2;
			mu = 2.23863e+11;
		}
		if (name.equals("Yb")) {
			charge = +3;
			mu = 1.41888e+11;
		}
		if (name.equals("Y")) {
			charge = +3;
			mu = 1.34102e+11;
		}
		if (name.equals("Zn")) {
			charge = +2;
			mu = 1.71304e+11;
		}

		cdadc = "1.0";
		phi = 0.0;
		c0 = 0.0;
		cL = 0.0;
	}

	/**
	 * Gets the charge of the ion, measured with reference to the electron. E.g., 1,
	 * 2, -1, -2.
	 * 
	 * @return the charge.
	 */
	public int getCharge() {
		return charge;
	}

	/**
	 * Gets the mobility of the ion, v/F, measured in m/s/N. This quantity is
	 * related to the electrophoretic mobility.
	 * 
	 * @return the mobility.
	 */
	public double getMu() {
		return mu;
	}

	/**
	 * Gets the parameter depending on the activity coefficient. This parameter is c
	 * d ln a/dc, where c is the concentration and a is the activity, both measured
	 * in M. In other words, it is the derivative of the logerithm of a with respect
	 * to c, multiplied by c. For dilute solutions, this parameter is 1.
	 * 
	 * @return the parameter cdadc.
	 */
	public String getCdadc() {
		return cdadc;
	}

	/**
	 * Gets the flux of the Ion. Only relative values matter.
	 * 
	 * @return the (relative) flux.
	 */
	public double getPhi() {
		return phi;
	}

	/**
	 * Gets the value of the concentration in M, at the left side of the junction
	 * (x=0).
	 * 
	 * @return the concentration c0 in molecules per cubic meter.
	 */
	public double getC0() {
		return c0;
	}

	/**
	 * Gets the value of the concentration in M, at the right side of the junction
	 * (x=L).
	 * 
	 * @return the concentration cL in molecules per cubic meter.
	 */
	public double getCL() {
		return cL;
	}

	/**
	 * Sets the charge of the ion, measured with reference to the electron. E.g., 1,
	 * 2, -1, -2.
	 * 
	 * @param v the charge.
	 */
	public void setCharge(int v) {
		charge = v;
	}

	/**
	 * Sets the mobility of the ion, v/F, measured in m/s/N. This quantity is
	 * related to the electrophoretic mobility.
	 * 
	 * @param v the mobility.
	 */
	public void setMu(double v) {
		mu = v;
	}

	/**
	 * Sets the parameter depending on the activity coefficient. This parameter is c
	 * d ln a/dc, where c is the concentration and a is the activity, both measured
	 * in M. In other words, it is the derivative of the logerithm of a with respect
	 * to c, multiplied by c. For dilute solutions, this parameter is 1.
	 * 
	 * @param v the parameter cdadc.
	 */
	public void setCdadc(String v) {
		cdadc = v;
	}

	/**
	 * Sets the flux of the Ion. Only relative values matter. However, it is useful
	 * to put the flux of the ions to the value CL-C0. In particular, this is done
	 * automatically, by the program, for the Ion that is selected as "X".
	 * 
	 * @param v the (relative) flux.
	 */
	public void setPhi(double v) {
		phi = v;
	}

	/**
	 * Sets the value of the concentration at the left side of the junction (x=0).
	 * 
	 * @param v the concentration c0 in molecules per cubic meter.
	 */
	public void setC0(double v) {
		c0 = v;
	}

	/**
	 * Sets the value of the concentration at the right side of the junction (x=L).
	 * 
	 * @param v the concentration cL in molecules per cubic meter.
	 */
	public void setCL(double v) {
		cL = v;
	}

	public String toString() {
		return name;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param n the name to be set.
	 */
	public void setName(String n) {
		name = n;
	}

}