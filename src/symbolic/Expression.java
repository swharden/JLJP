package symbolic;
 
import java.io.*;

/** The basic abstract class for doing analytical calculations.
*/

public abstract class Expression {

    /** Finds all the variables contained in this Expression.
	@return the list of the variables.
    */
    public abstract String [] getVariables();

    /** Differentiation of the expression with respect to the variables
     * @param vs list of variables, possibly repeated
     */
    public Expression differentiate(String [] vs) {
	Expression r = this;
	for (int j=0;j<vs.length;j++)
	    r=r.differentiate(vs[j]);
	return r;
    }

    /** Differentiation of the expression with respect to a variable
     * @param x the variable
     */
    public abstract Expression differentiate(String x);
	
    /** This method substitutes all the variables with the variable
     * values and calculates the result.
     * @param vs list of variables
     * @param xs list of values of the variables
     * @return the evaluated Expression.
     */
    public abstract double evaluate(String [] vs, double [] xs);

    /** This method substitutes a variables with an Expression.
     * @param v variables
     * @param e the expression to be substituted
     * @return the evaluated Expression.
     */
    public abstract Expression substitute(String v, Expression e);

    /** This method substitutes a variables with an Expression.
     * @param v variables
     * @param e the expression to be substituted
     * @return the evaluated Expression.
     */
    public Expression substitute(String v, String e) { return null; }


    /** Get the priority of the operator described by the expression.
     * This is needed only for allowing toString()
     * to generate the parenthesis when needed. 
     * @return the priority
     */
    protected abstract int getPriority();


}
