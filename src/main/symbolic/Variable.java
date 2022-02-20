package symbolic;

/** Variables in an Expression. */
public class Variable extends Expression {

    private final String name;

    /** 
     * @param n the name (symbol) of the variable
     */
    public Variable(String n) {
	name=n;
    }
    
    public String [] getVariables() {
	return new String [] {name};
    }

    public boolean equals(Object o) {
	if (o instanceof Variable) {
	    String so=((Variable)o).name;
	    if (so.equals(name)) 
		return true;
	}
	if (o instanceof String) {
	    String so=((String)o);
	    if (so.equals(name))
		return true;
	}
	return false;
    }

    public String toString() { return name; }

    public Expression differentiate(String x) {
	if (name.equals(x)) return new Constant(1.);
	else return new Constant(0.);
    }
    
    public double evaluate(String [] vs, double [] xs) {
	int f=-10;
	for (int j=0;j<vs.length;j++)
	    if (vs[j].equals(name))
		f=j;
	if (f<0) throw new Error("Variable "+name+" is undefined");
	return xs[f];
    }

    public Expression substitute(String v, Expression e) {
	if (v.equals(name)) 
	    return e;
	else return this;
    }

    protected int getPriority() {return 20;}

}
