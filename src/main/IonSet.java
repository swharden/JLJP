import java.io.*;
import java.text.*;
import java.util.*;
import symbolic.*;

/**
 * The definition of the solutions. The Ion s are added to the IonSet, and then
 * the calculation is performed.
 */

public class IonSet {

    // Physical costants
    private static final double KT = 1.3806488e-23 * (25.0 + 273.15);
    private static final double e = 1.6e-19;
    private static final double Nav = 6.02e23;
    private static final double epsilon = 8.854187817e-12 * 80.1;

    // Lista degli ioni
    private ArrayList<Ion> list;

    /**
     * Creates an empty IonSet. Must be then filled with the Ion s.
     */
    public IonSet() {
	list = new ArrayList<Ion>();
    }

    public String getDescription() {
	String msg = "";
	for (Ion ion : list) {
	    msg += ion.getDescription() + "\n";
	}
	return msg.trim();
    }

    /**
     * Adds an Ion to the IonSet. The last two Ion s have particular properties: are
     * the "X" and "Last" Ion s.
     * 
     * @param i the Ion to be added.
     */
    public void add(Ion i) {
	list.add(i);
    }

    /**
     * Number of ions.
     * 
     * @return number of Ion s.
     */
    public int size() {
	return list.size();
    }

    /**
     * Gets the n-th ion of the IonSet.
     * 
     * @return the Ion.
     */
    public Ion get(int j) {
	return list.get(j);
    }

    /**
     * Calculates the potential.
     * 
     * @return the voltage across the liquid junction.
     */

    public double calculate() throws Exception {
	return calculate(null);
    }

    /**
     * Calculates the potential. Also outputs a table, with the concentrations of
     * the ions along the liquid junction, and the voltage.
     * 
     * @param pst if not null, the table is sent here.
     * @return the voltage across the liquid junction.
     */

    public double calculate(PrintStream pst) throws Exception {

	// initial phi
	double[] phis = new double[list.size() - 2];
	for (int j = 0; j < list.size() - 2; j++)
	    phis[j] = list.get(j).getCL() - list.get(j).getC0();

	if (list.size() > 2) {
	    Solver s = new Solver(new PhiEquations());
	    s.solve(phis);
	}

	double[] cLs = new double[list.size() - 2];
	double r = 0.0;
	try {
	    r = givenPhi(pst, phis, cLs);
	} catch (Exception ex) {
	}

	// Updates and return
	for (int j = 0; j < list.size() - 2; j++)
	    list.get(j).setPhi(phis[j]);
	list.get(list.size() - 2).setPhi(list.get(list.size() - 2).getCL() - list.get(list.size() - 2).getC0());
	double somma = 0.0;
	for (int j = 0; j < list.size() - 1; j++)
	    somma += list.get(j).getPhi() * list.get(j).getCharge();
	list.get(list.size() - 1).setPhi(-somma / list.get(list.size() - 1).getCharge());
	somma = 0.0;
	for (int j = 0; j < list.size() - 1; j++)
	    somma += list.get(j).getCL() * list.get(j).getCharge();
	list.get(list.size() - 1).setCL(-somma / list.get(list.size() - 1).getCharge());

	return r;
    }

    // Problem for finding the phis.
    private class PhiEquations implements EquationSystem {
	private double[] sigma;
	private String[] vars;
	private Expression[] eCdadc;

	public PhiEquations() throws Exception {
	    // sigma
	    double min = -10.0;
	    for (int j = 0; j < list.size(); j++)
		if (list.get(j).getCL() != 0.0)
		    if (min < 0.0 || Math.abs(list.get(j).getCL()) < min)
			min = Math.abs(list.get(j).getCL());
	    sigma = new double[list.size()];
	    for (int j = 0; j < list.size(); j++)
		if (list.get(j).getCL() != 0.0)
		    sigma[j] = 0.01 * Math.abs(list.get(j).getCL());
		else
		    sigma[j] = 0.01 * min;
	    // symbolic expressions
	    vars = new String[list.size()];
	    eCdadc = new Expression[list.size()];
	    symbolicParsing(vars, eCdadc);
	}

	public void equations(double[] x, double[] f) throws Exception {
	    givenPhi(null, x, f, vars, eCdadc);
	    for (int j = 0; j < list.size() - 2; j++) {
		f[j] -= list.get(j).getCL();
		f[j] /= sigma[j];
	    }
	}

	public int number() {
	    return list.size() - 2;
	}
    }

    // define phi, and calculate the resulting cLs.
    private double givenPhi(PrintStream pst, double[] phis, double[] cLs) throws Exception {
	String[] vars = new String[list.size()];
	Expression[] eCdadc = new Expression[list.size()];
	symbolicParsing(vars, eCdadc);
	return givenPhi(pst, phis, cLs, vars, eCdadc);
    }

    private void symbolicParsing(String[] vars, Expression[] eCdadc) throws Exception {
	for (int j = 0; j < list.size(); j++) {
	    vars[j] = list.get(j).toString();
	    eCdadc[j] = ExpressionParser.parse(list.get(j).getCdadc());
	}
    }

    private double givenPhi(PrintStream pst, double[] phis, double[] cLs, String[] vars, Expression[] eCdadc)
	throws Exception {

	assert phis.length != list.size() - 2;
	assert cLs.length != list.size() - 2;

	// indices
	int iCl = list.size() - 1;
	int iK = list.size() - 2;
	int num = list.size() - 1;

	// parameters

	String varCl = vars[iCl];
	Expression eCdadcCl = eCdadc[iCl];

	double[] z = new double[num];
	for (int j = 0; j < num; j++)
	    z[j]=list.get(j).getCharge();
	double zCl = list.get(iCl).getCharge();

	double[] mu = new double[num];
	for (int j = 0; j < num; j++)
	    mu[j]=list.get(j).getMu();
	double muCl = list.get(iCl).getMu();

	double[] cdadc = new double[num];
	double cdadcCl;

	double[] phi = new double[num];
	for (int j = 0; j < num - 1; j++)
	    phi[j]=phis[j];
	phi[num - 1]=list.get(iK).getCL() - list.get(iK).getC0();
	double phiCl = -Linalg.scalarProd(z,phi) / zCl;

	double KC0 = list.get(iK).getC0();
	double KCL = list.get(iK).getCL();
	double dK = (KCL - KC0) / 1000.0;

	assert dK != 0;

	// inizialization
	double[] rho = new double[num];
	for (int j = 0; j < num; j++)
	    rho[j]=list.get(j).getC0();
	double rhoCl = -Linalg.scalarProd(z,rho) / zCl;
	list.get(iCl).setC0(rhoCl);
	double V = 0.0;

	// cycle
	for (double rhoK = KC0; ((dK > 0) ? rhoK <= KCL : rhoK >= KCL); rhoK += dK) {

	    rhoCl = -Linalg.scalarProd(rho,z) / zCl;

	    if (pst != null) {
		for (int j = 0; j < num; j++)
		    pst.print(rho[j] / 1000.0 / Nav + " ");
		pst.println(rhoCl / 1000.0 / Nav + " " + V);
	    }

	    // c dadc

	    double[] vals=new double[vars.length];
	    for (int j = 0; j < num; j++)
		vals[j]=rho[j] / 1000.0 / Nav;
	    vals[iCl]=rhoCl / 1000.0 / Nav;

	    for (int j = 0; j < num; j++)
		cdadc[j]=eCdadc[j].evaluate(vars, vals);
	    cdadcCl=eCdadcCl.evaluate(vars, vals);

	    // linear equation
	    double[][] mD = new double[num][num];
	    for (int j = 0; j < num; j++)
		for (int k = 0; k < num; k++)
		    mD[j][k]=0.0;
	    for (int j = 0; j < num; j++)
		mD[j][j]=mu[j] * KT * cdadc[j];
	    double DCl = muCl * KT * cdadcCl;

	    double[] v = new double[num];
	    for (int j = 0; j < num; j++)
		v[j]= z[j] * e * mu[j] * rho[j];
	    double vCl = zCl * e * muCl * rhoCl;

	    if (Linalg.scalarProd(z,v) + zCl * vCl == 0.0)
		throw new Exception("Singularity; unable to continue the calculation");

	    double[] Delta = Linalg.scalarMultiply(Linalg.prod(Linalg.sum(1.,mD,-DCl,Linalg.identity(num)), z),
						   1./(Linalg.scalarProd(z,v) + zCl * vCl)
						   );
	    double[][] mDyadic = new double[num][num];
	    for (int j = 0; j < num; j++)
		for (int k = 0; k < num; k++)
		    mDyadic[j][k]=v[j] * Delta[k];
	double[][] mM = Linalg.sum(1.,mDyadic,-1.,mD);
	// solve
	double[] rhop = Linalg.solve(mM, phi);
	double rhopCl = -Linalg.scalarProd(z,rhop) / zCl;
	double rhopK = rhop[iK];
	// results
	double E = Linalg.scalarProd(Delta,rhop);
	rho = Linalg.sum(1., rho, dK/rhopK, rhop);
	V -= E * dK / rhopK;
    }

    // store the results
    for (int j = 0; j < num - 1; j++)
	cLs[j] = rho[j];

    return V;
}

}
