import java.util.*;

public class Example {

    public static void main(String[] args) throws Exception {

        var ionSet = new ArrayList<Ion>();
        ionSet.add(new Ion("Zn", 9, 0));
        ionSet.add(new Ion("K", 0, 3));
        ionSet.add(new Ion("Cl", 18, 3));

        double ljpV = LjpMath.SolveAndCalculateLjp(ionSet);
        System.err.println(String.format("LJP = %f mV", ljpV * 1000.0));

        double expectedLjpV = -0.02082307995;
        if (Math.abs(expectedLjpV - ljpV) >= .00001)
            System.err.println(String.format("ERROR: expected %f V", expectedLjpV));

    }

}