import java.io.*;
import java.text.*;
import java.util.*;
import JSci.maths.*;
import JSci.maths.matrices.*;
import JSci.maths.vectors.*;
import JSci.maths.symbolic.*;
import JSci.maths.fields.*;

/** The definition of the solutions. The Ion s are added to the
IonSet, and then the calculation is performed. */

public class IonSet {

    // Physical costants
    private static final double KT=1.3806488e-23*(25.0+273.15);
    private static final double e=1.6e-19;
    private static final double Nav=6.02e23;
    private static final double epsilon=8.854187817e-12*80.1;

    // Lista degli ioni
    private ArrayList<Ion> list;


    /** Creates an empty IonSet. Must be then filled with the Ion s.
    */
    public IonSet() {
	list = new ArrayList<Ion>();
    }
    
    /** Adds an Ion to the IonSet. The last two Ion s have particular
	properties: are the "X" and "Last" Ion s.
	@param i the Ion to be added.
    */
    public void add(Ion i) {
	list.add(i);
    }

    /** Number of ions.
	@return number of Ion s.
    */
    public int size() { return list.size(); }

    /** Gets the n-th ion of the IonSet.
	@return the Ion.
    */
    public Ion get(int j) { return list.get(j); }


    /** 
	Calculates the potential.
	@return the voltage across the liquid junction.
    */

    public double calculate() throws Exception {
	return calculate(null);
    }

    /** 
	Calculates the potential. Also outputs a table, with the
	concentrations of the ions along the liquid
	junction, and the voltage.
	@param pst if not null, the table is sent here.
	@return the voltage across the liquid junction.
    */
    
    public double calculate(PrintStream pst) throws Exception {

	// initial phi
	double [] phis=new double[list.size()-2];
	for (int j=0;j<list.size()-2;j++)
	    phis[j]=list.get(j).getCL()-list.get(j).getC0();
	
	if (list.size()>2) {
	    Solver s=new Solver(new PhiEquations());
	    s.solve(phis);
	}

	double [] cLs=new double[list.size()-2];
       	double r=0.0;
	try {
	    r=givenPhi(pst, phis, cLs);
	} catch (Exception ex) {}

	// Updates and return
	for (int j=0;j<list.size()-2;j++) 
	    list.get(j).setPhi(phis[j]);
	list.get(list.size()-2).setPhi(list.get(list.size()-2).getCL()-list.get(list.size()-2).getC0());
	double somma=0.0;
	for (int j=0;j<list.size()-1;j++) 
	    somma+=list.get(j).getPhi()*list.get(j).getCharge();
	list.get(list.size()-1).setPhi(-somma/list.get(list.size()-1).getCharge());
	somma=0.0;
	for (int j=0;j<list.size()-1;j++) 
	    somma+=list.get(j).getCL()*list.get(j).getCharge();
	list.get(list.size()-1).setCL(-somma/list.get(list.size()-1).getCharge());

	return r;
    }



    // Problem for finding the phis.
    private class PhiEquations implements EquationSystem {
	private double [] sigma;
	private Variable [] var;
	private Expression [] eCdadc;
	public PhiEquations() throws Exception {
	    // sigma
	    double min=-10.0;
	    for (int j=0;j<list.size();j++) 
		if (list.get(j).getCL()!=0.0)
		    if (min<0.0 ||Math.abs(list.get(j).getCL())<min)
			min=Math.abs(list.get(j).getCL());
	    sigma=new double[list.size()];
	    for (int j=0;j<list.size();j++)
		if (list.get(j).getCL()!=0.0)
		    sigma[j]=0.01*Math.abs(list.get(j).getCL());
		else
		    sigma[j]=0.01*min;
	    // symbolic expressions
	    var=new Variable[list.size()];
	    eCdadc=new Expression[list.size()];
	    symbolicParsing(var, eCdadc);
	}
	public void equations(double [] x,double [] f) throws Exception {
	    givenPhi(null,x,f,var,eCdadc);
	    for (int j=0;j<list.size()-2;j++) {
		f[j]-=list.get(j).getCL();
		f[j]/=sigma[j];
	    }
	}
	public int number() { return list.size()-2; }
    }


    // define phi, and calculate the resulting cLs.
    private double givenPhi(PrintStream pst, double [] phis, double [] cLs) throws Exception {
	Variable [] var=new Variable[list.size()];
	Expression [] eCdadc=new Expression[list.size()];
	symbolicParsing(var, eCdadc);
	return givenPhi(pst, phis, cLs, var, eCdadc);
    }

    private void symbolicParsing(Variable [] var, Expression [] eCdadc) throws Exception {

	for (int j=0;j<list.size();j++)
	    var[j]=new Variable(list.get(j).toString(), RealField.getInstance());
	Hashtable vht=new Hashtable();
	for (int j=0;j<list.size();j++) vht.put(var[j].toString(),var[j]);

	for (int j=0;j<list.size();j++)
	    eCdadc[j]=ExpressionParser.parse(list.get(j).getCdadc(), vht);
    }

	
	
    private double givenPhi(PrintStream pst, double [] phis, double [] cLs, Variable [] var, Expression [] eCdadc) throws Exception {
	    
	assert phis.length!=list.size()-2;
	assert cLs.length!=list.size()-2;

	// indices
	int iCl=list.size()-1;
	int iK=list.size()-2;
	int num=list.size()-1;

	// parameters

	Variable varCl=var[iCl];
	Expression eCdadcCl=eCdadc[iCl];

	DoubleVector z=new DoubleVector(num);
	for (int j=0;j<num;j++) 
	    z.setComponent(j,list.get(j).getCharge());
	double zCl=list.get(iCl).getCharge();

	DoubleVector mu=new DoubleVector(num);
	for (int j=0;j<num;j++) 
	    mu.setComponent(j,list.get(j).getMu());
	double muCl=list.get(iCl).getMu();

	DoubleVector cdadc=new DoubleVector(num);
	double cdadcCl;

	DoubleVector phi=new DoubleVector(num);
	for (int j=0;j<num-1;j++)
	    phi.setComponent(j,phis[j]);
	phi.setComponent(num-1, list.get(iK).getCL()-list.get(iK).getC0());
	double phiCl=-z.scalarProduct(phi)/zCl;

	double KC0=list.get(iK).getC0();
	double KCL=list.get(iK).getCL();
	double dK=(KCL-KC0)/1000.0;
	
	assert dK!=0;

	// inizialization
	AbstractDoubleVector rho=new DoubleVector(num);
	for (int j=0;j<num;j++) 
	    rho.setComponent(j,list.get(j).getC0());
	double rhoCl=-z.scalarProduct(rho)/zCl;
	list.get(iCl).setC0(rhoCl);
	double V=0.0;

	// cycle
	for (double rhoK=KC0;((dK>0)?rhoK<=KCL:rhoK>=KCL);rhoK+=dK) {

	    rhoCl=-rho.scalarProduct(z)/zCl;

	    if (pst!=null) {
		for (int j=0;j<num;j++)
		    pst.print(rho.getComponent(j)/1000.0/Nav+" ");
		pst.println(rhoCl/1000.0/Nav+" "+V);
	    }

	    // c dadc

	    for (int j=0;j<num;j++) 
		var[j].setValue(new MathDouble(rho.getComponent(j)/1000.0/Nav));
	    varCl.setValue(new MathDouble(rhoCl/1000.0/Nav));

	    for (int j=0;j<num;j++) 
		cdadc.setComponent(j,((MathDouble)(((Constant)(eCdadc[j].evaluate())).getValue())).doubleValue());
	    cdadcCl=((MathDouble)(((Constant)(eCdadcCl.evaluate())).getValue())).doubleValue();

	    // linear equation
	    DoubleSquareMatrix mD=new DoubleSquareMatrix(num);
	    for (int j=0;j<num;j++) 
		for (int k=0;k<num;k++)
		    mD.setElement(j,k,0.0);
	    for (int j=0;j<num;j++) 
		mD.setElement(j,j,mu.getComponent(j)*KT*cdadc.getComponent(j));
	    double DCl=muCl*KT*cdadcCl;
	    
	    DoubleVector v=new DoubleVector(num);
	    for (int j=0;j<num;j++) 
		v.setComponent(j,z.getComponent(j)*e*mu.getComponent(j)*rho.getComponent(j));
	    double vCl=zCl*e*muCl*rhoCl;

	    if (z.scalarProduct(v)+zCl*vCl==0.0) throw new Exception("Singularity; unable to continue the calculation");

	    AbstractDoubleVector Delta=mD.subtract(DoubleDiagonalMatrix.identity(num).scalarMultiply(DCl)).multiply(z).scalarDivide(z.scalarProduct(v)+zCl*vCl);
	    DoubleSquareMatrix mDyadic=new DoubleSquareMatrix(num);
	    for (int j=0;j<num;j++) 
		for (int k=0;k<num;k++)
		    mDyadic.setElement(j,k,v.getComponent(j)*Delta.getComponent(k));
	    AbstractDoubleSquareMatrix mM=(AbstractDoubleSquareMatrix)mDyadic.subtract(mD);
	    // solve
	    AbstractDoubleVector rhop=LinearMath.solve(mM, phi);
	    double rhopCl=-z.scalarProduct(rhop)/zCl;
	    double rhopK=rhop.getComponent(iK);
	    // results
	    double E=Delta.scalarProduct(rhop);
	    rho=rho.add(rhop.scalarMultiply(dK/rhopK));
	    V-=E*dK/rhopK;
	}

	// store the results
	for (int j=0;j<num-1;j++) 
	    cLs[j]=rho.getComponent(j);
	
	return V;
    }


}