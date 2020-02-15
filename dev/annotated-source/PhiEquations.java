import java.util.ArrayList;

class PhiEquations implements EquationSystem {

    private ArrayList<Ion> list;
    private int ionCount;
    private double[] sigma;

    public PhiEquations(ArrayList<Ion> list) throws Exception {

        this.list = list;
        this.ionCount = list.size();

        // determine smallest nonzero cL of all ions
        double smallestCL = Double.POSITIVE_INFINITY;
        for (int j = 0; j < ionCount; j++) {
            Ion ion = list.get(j);
            boolean ionHasCL = (ion.cL > 0);
            if (ionHasCL) {
                smallestCL = Math.min(smallestCL, Math.abs(ion.cL));
            }
        }

        // set sigmas to cLs (unless cL is zero, then use smallest nonzero cL)
        sigma = new double[ionCount];
        for (int j = 0; j < ionCount; j++) {
            Ion ion = list.get(j);
            boolean ionHasCL = (ion.cL > 0);
            if (ionHasCL)
                sigma[j] = 0.01 * Math.abs(ion.cL);
            else
                sigma[j] = 0.01 * smallestCL;
        }

    }

    public void equations(double[] x, double[] f) throws Exception {
        double ljp_V = LjpMath.calculateJunctionVoltage(list, x, f);
        for (int j = 0; j < ionCount - 2; j++) {
            Ion ion = list.get(j);
            f[j] = (f[j] - ion.cL) / sigma[j];
        }
    }

    public int number() {
        return list.size() - 2;
    }
}