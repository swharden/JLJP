
public class PhysicalConstants {

    public static final double zeroCinK = 273.15;
    public static final double temperatureC = 25.0;
    public static final double temperatureK = zeroCinK + temperatureC;

    // Boltzmann constant = 1.38064852e-23 (m^2 * kg) / (s^2 * K)
    public static final double boltzmann = 1.3806488e-23;
    public static final double KT = boltzmann * temperatureK;

    // Elementary charge = 1.602176634×10−19 (cm * g * s)
    public static final double e = 1.6e-19;

    // Avogadro constant = 6.02214076e23 (no units)
    public static final double Nav = 6.02e23;
    
}
