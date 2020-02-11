package symbolic;

/** A function like sin(x), exp(x) or sqrt(x).
*/
public class Function extends Expression {

    public static final int SIN = 0;
    public static final int COS = 1;
    public static final int TAN = 2;
    public static final int ASIN = 3;
    public static final int ACOS = 4;
    public static final int ATAN = 5;
    public static final int SINH = 6;
    public static final int COSH = 7;
    public static final int TANH = 8;
    public static final int ASINH = 9;
    public static final int ACOSH = 10;
    public static final int ATANH = 11;
    public static final int EXP = 12;
    public static final int LOG = 13;
    public static final int SQRT = 14;

    private final int type;
    private final Expression arg;

    public String [] getVariables() { 
	return arg.getVariables(); 
    }

    /**
	* @param n the type of the function; for example, Function.SIN
	* @param a the argument
	*/
    public Function(int n, Expression a) {
	type=n;
	arg=a;
    }


    /**
     * @param n the name of the function; for example, "sin"
     * @param a the argument
     */
    public Function(String n, Expression a) {

	int t=-10;
	if (n.equals("sin"))t=0;
	if (n.equals("cos"))t=1;
	if (n.equals("tan"))t=2;
	if (n.equals("asin"))t=3;
	if (n.equals("acos"))t=4;
	if (n.equals("atan"))t=5;
	if (n.equals("sinh"))t=6;
	if (n.equals("cosh"))t=7;
	if (n.equals("tanh"))t=8;
	if (n.equals("asinh"))t=9;
	if (n.equals("acosh"))t=10;
	if (n.equals("atanh"))t=11;
	if (n.equals("exp"))t=12;
	if (n.equals("log"))t=13;
	if (n.equals("sqrt"))t=14;
	if (t<0) throw new Error("Unrecognized function name");

	type=t;
	arg=a;

    }


    public String toString() { 
	String name="UNKNOWN";
	switch (type) {
	case SIN: name="sin"; break;
	case COS: name="cos"; break;
	case TAN: name="tan"; break;
	case ASIN: name="asin"; break;
	case ACOS: name="acos"; break;
	case ATAN: name="atan"; break;
	case SINH: name="sinh"; break;
	case COSH: name="cosh"; break;
	case TANH: name="tanh"; break;
	case ASINH: name="asinh"; break;
	case ACOSH: name="acosh"; break;
	case ATANH: name="atanh"; break;
	case EXP: name="exp"; break;
	case LOG: name="log"; break;
	case SQRT: name="sqrt"; break;
	}
	return name+"("+arg+")"; 
    }

    public int getPriority() {return 15;}

    private static Expression inverse(Expression e) { return new Power(e, -1); }

    public Expression differentiate(String x) {
	Expression d=null;
	switch (type) {
	case SIN: d=new Function(COS,arg); break;
	case COS: d=new Negative(new Function(SIN,arg)); break;
	case TAN: d=new Power(new Function(COS,arg),-2); break;
	case ASIN: d=inverse(new Function(SQRT,new Sum(new Constant(1.), new Negative(new Power(arg,2)) ))); break;
	case ACOS: d=inverse(new Function(SQRT,new Sum(new Constant(1.), new Negative(new Power(arg,2)) ) )); break;
	case ATAN: d=inverse(new Sum( new Constant(1.), new Power(arg,2) )); break;
	case SINH: d=new Function(COSH,arg); break;
	case COSH: d=new Function(SINH,arg); break;
	case TANH: d=new Power(new Function(COSH,arg),-2); break;
	case ASINH: d=inverse(new Function(SQRT,new Sum(new Constant(1.),new Power(arg,2)))); break;
    	case ACOSH: d=inverse(new Function(SQRT,new Sum(new Constant(-1.), new Power(arg,2)))); break;
	case ATANH: d=inverse(new Function(SQRT,new Sum(new Constant(1.), new Negative(new Power(arg,2))))); break;
	case EXP: d=new Function(EXP,arg); break;
	case LOG: d=inverse(arg); break;
	case SQRT: d=inverse(new Product( new Constant(2.), new Function(SQRT,arg))); break;
	}
	if (d==null) 
	    throw new IllegalArgumentException("Unknown Function type in derivative()");
	return new Product(d,arg.differentiate(x));
    }

    public boolean equals(Object o) {
	if (o instanceof Function) {
	    Function f = (Function)o;
	    return ( (type == f.type) && arg.equals(f.arg));
	}
	return false;
    }
    
    public double evaluate(String [] vs, double [] xs) {
	double sarg = arg.evaluate(vs, xs);

	switch (type) {
	case SIN: return Math.sin(sarg);
	case COS: return Math.cos(sarg);
	case TAN: return Math.tan(sarg);
	case ASIN: return Math.asin(sarg);
	case ACOS: return Math.acos(sarg);
	case ATAN: return Math.atan(sarg);
	case SINH: return Math.sinh(sarg);
	case COSH: return Math.cosh(sarg);
	case TANH: return Math.tanh(sarg);
	case ASINH: return Double.NaN; //Math.asinh(sarg);
	case ACOSH: return Double.NaN; // Math.acosh(sarg);
	case ATANH: return Double.NaN; // Math.atanh(sarg);
	case EXP: return Math.exp(sarg);
	case LOG: return Math.log(sarg);
	case SQRT: return Math.sqrt(sarg);
	}

	return Double.NaN;
    }


    public Expression substitute(String v, Expression e) { 
	return new Function(type, arg.substitute(v,e));
    }



}	



