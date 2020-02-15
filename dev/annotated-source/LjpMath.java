import java.util.ArrayList;

public class LjpMath {

    public static double SolveAndCalculateLjp(ArrayList<Ion> list) throws Exception {
        /*
         * This method solves for phis to balance equations before calculating LJP, then
         * updates all the ions in the given list based on these results.
         */

        int ionCount = list.size();
        int ionCountMinusOne = ionCount - 1;
        int ionCountMinusTwo = ionCount - 2;

        Ion secondFromLastIon = list.get(list.size() - 2);
        Ion LastIon = list.get(list.size() - 1);

        // phis will be solved for all ions except the last two
        double[] phis = new double[ionCountMinusTwo];

        // phis to solve are initialized to the concentration difference
        for (int j = 0; j < phis.length; j++) {
            Ion ion = list.get(j);
            phis[j] = ion.cL - ion.c0;
        }

        // all phis except the last two get solved
        if (ionCount > 2) {
            Solver s = new Solver(new PhiEquations(list));
            s.solve(phis);
        }

        // calculate LJP
        double[] cLs = new double[phis.length];
        double ljp_V = LjpMath.calculateJunctionVoltage(list, phis, cLs);
        if (ljp_V == Double.NaN)
            throw new Exception("ERROR: Singularity (calculation aborted)");

        // update ions based on what was just calculated
        for (int j = 0; j < phis.length; j++) {
            Ion ion = list.get(j);
            ion.phi = phis[j];
            // ion.cL = cLs[j];
        }

        // second from last ion phi is concentration difference
        secondFromLastIon.phi = secondFromLastIon.cL - secondFromLastIon.c0;

        // last ion's phi is calculated from all the phis before it
        double totalChargeWeightedPhi = 0.0;
        for (int j = 0; j < ionCountMinusOne; j++) {
            Ion ion = list.get(j);
            totalChargeWeightedPhi += ion.phi * ion.charge;
        }
        LastIon.phi = -totalChargeWeightedPhi / LastIon.charge;

        // last ion's cL is calculated from all the cLs before it
        double totalChargeWeightedCL = 0.0;
        for (int j = 0; j < ionCountMinusOne; j++) {
            Ion ion = list.get(j);
            totalChargeWeightedCL += ion.cL * ion.charge;
        }
        LastIon.cL = -totalChargeWeightedCL / LastIon.charge;

        return ljp_V;
    }

    public static double calculateJunctionVoltage(ArrayList<Ion> list, double[] startingPhis, double[] startingCLs) {
        /*
         * Uses given phis and CLs to try to determine LJP for the list of ions.
         * WARNING: this function does not change phis but does change CLs.
         */

        double cdadc = 1.0; // fine for low concentrations

        int ionCount = list.size();
        int ionCountMinusOne = ionCount - 1;
        int ionCountMinusTwo = ionCount - 2;
        int indexLastIon = ionCount - 1;
        int indexSecondFromLastIon = ionCount - 2;

        Ion lastIon = list.get(indexLastIon);
        Ion secondFromLastIon = list.get(indexSecondFromLastIon);

        assert startingPhis.length != ionCount - 2;
        assert startingCLs.length != ionCount - 2;

        // populate charges, mus, and rhos from all ions except the last one
        double[] charges = new double[ionCountMinusOne];
        double[] mus = new double[ionCountMinusOne];
        double[] rhos = new double[ionCountMinusOne];
        for (int j = 0; j < ionCountMinusOne; j++) {
            Ion ion = list.get(j);
            charges[j] = ion.charge;
            mus[j] = ion.mu;
            rhos[j] = ion.c0;
        }

        // populate phis from all ions except the last two
        double[] phis = new double[ionCountMinusOne];
        for (int j = 0; j < ionCountMinusTwo; j++)
            phis[j] = startingPhis[j];

        // second from last phi is the concentration difference
        phis[indexSecondFromLastIon] = secondFromLastIon.cL - secondFromLastIon.c0;

        // prepare info about second from last ion concentration difference for loop
        double KC0 = secondFromLastIon.c0;
        double KCL = secondFromLastIon.cL;
        double dK = (KCL - KC0) / 1000.0;
        assert dK != 0;

        // set last ion C0 based on charges, rhos, and linear algebra
        double zCl = list.get(indexLastIon).charge;
        double rhoCl = -Linalg.scalarProd(charges, rhos) / zCl;
        lastIon.c0 = rhoCl;

        // cycle to determine junction voltage

        double V = 0.0;
        for (double rhoK = KC0; ((dK > 0) ? rhoK <= KCL : rhoK >= KCL); rhoK += dK) {

            rhoCl = -Linalg.scalarProd(rhos, charges) / zCl;

            double DCl = lastIon.mu * PhysicalConstants.KT * cdadc;
            double vCl = zCl * PhysicalConstants.e * lastIon.mu * rhoCl;

            double[] v = new double[ionCountMinusOne];
            double[][] mD = new double[ionCountMinusOne][ionCountMinusOne];

            for (int j = 0; j < ionCountMinusOne; j++)
                for (int k = 0; k < ionCountMinusOne; k++)
                    mD[j][k] = 0.0;

            for (int j = 0; j < ionCountMinusOne; j++) {
                for (int k = 0; k < ionCountMinusOne; k++)
                    mD[j][k] = 0.0;
                mD[j][j] = mus[j] * PhysicalConstants.KT * cdadc;
                v[j] = charges[j] * PhysicalConstants.e * mus[j] * rhos[j];
            }

            if (Linalg.scalarProd(charges, v) + zCl * vCl == 0.0) {
                return Double.NaN; // Singularity; abort calculation
            }

            double[][] identity = Linalg.identity(ionCountMinusOne);
            double[][] linAlgSum = Linalg.sum(1., mD, -DCl, identity);
            double[] sumCharge = Linalg.prod(linAlgSum, charges);
            double chargesProd = Linalg.scalarProd(charges, v);
            double chargesProdPlusCl = chargesProd + zCl * vCl;
            double[] delta = Linalg.scalarMultiply(sumCharge, 1.0 / chargesProdPlusCl);

            double[][] mDyadic = new double[ionCountMinusOne][ionCountMinusOne];
            for (int j = 0; j < ionCountMinusOne; j++)
                for (int k = 0; k < ionCountMinusOne; k++)
                    mDyadic[j][k] = v[j] * delta[k];
            double[][] mM = Linalg.sum(1., mDyadic, -1., mD);

            double[] rhop = Linalg.solve(mM, phis);
            double rhopK = rhop[indexSecondFromLastIon];
            rhos = Linalg.sum(1., rhos, dK / rhopK, rhop);

            double E = Linalg.scalarProd(delta, rhop);
            V -= E * dK / rhopK;

        }

        // modify CLs based on the rhos we calculated
        for (int j = 0; j < ionCountMinusTwo; j++)
            startingCLs[j] = rhos[j];

        return V;
    }

}