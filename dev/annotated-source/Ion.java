public class Ion {

    public String name;
    public int charge;
    public double mu;
    public double phi;
    public double c0;
    public double cL;

    public Ion(String n, double c0, double cL) {
        Initialize(n, c0, cL);
    }

    public Ion(String n) {
        Initialize(n, 0, 0);
    }

    private void Initialize(String name, double c0, double cL) {
        this.name = name;
        this.c0 = c0;
        this.cL = cL;
        double[] chargeAndMu = IonTable.LookupChargeAndMu(name);
        charge = (int) chargeAndMu[0];
        mu = chargeAndMu[1];
    }

}
