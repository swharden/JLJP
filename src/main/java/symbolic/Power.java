package symbolic;
 
public class Power extends Expression {

    private final Expression base;
    private final int exponent;

    public Power(Expression b, int e) {
	base=b;
	exponent=e;
    }

    public String [] getVariables() { 
	return base.getVariables(); 
    }


    public Expression getBase() {return base;}
    public int getExponent() {return exponent;}

    public String toString() { 
	String r = "";
	int ep;
	if (base.getPriority()<getPriority()) r+="("+base+")";
	else  r+=""+base;
	r+="^";
	if (exponent>=0) ep=20; else ep=0;
	if (ep<getPriority()) r+="("+exponent+")";
	else  r+=""+exponent;
	return r;
    }

    public Expression differentiate(String x) {
	return new Product(new Expression[] {
	    new Constant(exponent),
	    new Power(base,exponent-1),
	    base.differentiate(x)
	    });
    }

    public double evaluate(String [] vs, double [] xs) {
	return Math.pow(base.evaluate(vs, xs), exponent);
    }

    public Expression substitute(String v, Expression e) {
	Expression b = base.substitute(v, e);
	if (exponent==0) return new Constant(1.);
	if (exponent==1) return b;
	return new Power(b,exponent);
    }

    protected int getPriority() {return 15;}

}



