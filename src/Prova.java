class Prova implements EquationSystem {
    private int num=0;
    private double a0, a1, a2, delta;
    public void equations(double [] x,double [] f) throws Exception {
	num++;
	f[0]=(x[0]+x[0]*x[1]*3.0+x[0]*x[0]*x[0]-a0)*delta;
	f[1]=(x[1]+x[0]*x[1]*2.0+x[1]*x[1]*x[1]-a1)*delta;
    }
    
    public int number() { return 2; }

    public static void main(String [] args) throws Exception {
	Prova e=new Prova();
	double []x=new double[2];
	x[0]=1.0; x[1]=1.0;
	double [] f=new double[2];
	e.a0=e.a1=0.0;
	e.delta=1.0;
	e.equations(x,f);
	e.a0=f[0]; e.a1=f[1];
	e.delta=1e4;
	Solver s=new Solver(e);
	x[0]=2.0; x[1]=3.0;
	s.solve(x);
	System.err.println("Steps: "+e.num);
	e.equations(x,f);
	System.err.println("Scarto: "+f[0]);
	System.err.println("Scarto: "+f[1]);
    }

}

