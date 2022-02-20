
/**
 * A system of equations that can be solved by the Solver
 */
public interface EquationSystem {
	/**
	 * The vectorial equations in the form f(x)=0. The required error is 1: the
	 * reported solution is a value of x, such that f_j(x) is between -1 and 1.
	 * 
	 * @param x the unknowns
	 * @param f the value of f(x) that must be zero
	 */
	public void equations(double[] x, double[] f) throws Exception;

	/**
	 * Gets the number of equations and unknowns.
	 * 
	 * @return the number of equations.
	 */
	public int number();
}
