package symbolic;

/** This class wraps a double value. */
public class Constant extends Expression {

    private final double value;

    public String [] getVariables() { 
	return new String[0]; 
    }

    /** A constant Expression
     * @param x the constant value
     */
    public Constant(double x) {
	value=x;
    }

    public String toString() { 
	return Double.toString(value);
    }

    public Expression differentiate(String x) {
	return new Constant(0.);
    }

    public double evaluate(String [] vs, double [] xs) {
	return value;
    }

    public Expression substitute(String v, Expression e) {
	return this;
    }

    protected int getPriority() { 
	String sv=Double.toString(value);
        if (
            sv.indexOf("+")!=-1 ||
            sv.indexOf("-")!=-1
            ) return 0;
        if (sv.indexOf("*")!=-1) return 10;
        if (sv.indexOf("^")!=-1) return 15;
        return 20;
    }

    public double getValue() { return value; }

    public boolean equals(Object o) {
	if (o instanceof Constant) {
	    double op=((Constant)o).getValue();
	    return (value == op);
	}
	else return false;
    }

}




