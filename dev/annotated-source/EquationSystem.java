public interface EquationSystem {

    public void equations(double[] x, double[] f) throws Exception; // equations in the form f(x)=0

    public int number(); // number of equations and unknowns

}
