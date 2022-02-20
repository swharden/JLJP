package symbolic;

import java.util.*;

public class Negative extends Expression {

	private final Expression arg;

	public String[] getVariables() {
		return arg.getVariables();
	}

	public Negative(Expression arg) {
		this.arg = arg;
	}

	public String toString() {
		String r = "-";
		if (arg.getPriority() > getPriority())
			r += "(" + arg + ")";
		else
			r += "" + arg;
		return r;
	}

	public Expression differentiate(String x) {
		return new Negative(arg.differentiate(x));
	}

	public double evaluate(String[] vs, double[] xs) {
		return -arg.evaluate(vs, xs);
	}

	public Expression substitute(String v, Expression e) {
		return new Negative(arg.substitute(v, e));
	}

	protected int getPriority() {
		return -1;
	}

}
