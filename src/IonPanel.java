import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.applet.*;
import java.text.*;

/** A JPanel for editing the parameters of an Ion. */

public class IonPanel extends JPanel {

    private static final double KT = 1.3806488e-23 * (25.0 + 273.15);
    private static final double e = 1.6e-19;
    private static final double Nav = 6.02e23;
    private static final double epsilon = 8.854187817e-12 * 80.1;

    private JFormattedTextField charge, mu, phi, c0, cL;
    private JTextField name, cdadc;

    public final JRadioButton isX = new JRadioButton();
    public final JRadioButton isLast = new JRadioButton();
    public final JButton remove = new JButton("Remove");

    /**
     * Create the graphical element for handling the parameters of the Ions.
     */
    public IonPanel(String nm) {

        name = new JTextField();
        charge = new JFormattedTextField(new DecimalFormat("0.###E0"));
        mu = new JFormattedTextField(new DecimalFormat("0.###E0"));
        cdadc = new JTextField();
        phi = new JFormattedTextField(new DecimalFormat("0.###E0"));
        c0 = new JFormattedTextField(new DecimalFormat("0.###E0"));
        cL = new JFormattedTextField(new DecimalFormat("0.###E0"));

        name.setColumns(6);
        charge.setColumns(6);
        mu.setColumns(6);
        cdadc.setColumns(6);
        phi.setColumns(6);
        c0.setColumns(6);
        cL.setColumns(6);

        setLayout(new GridLayout(1, 10));

        add(name);
        add(charge);
        add(mu);
        add(cdadc);
        add(phi);
        add(c0);
        add(cL);
        add(isX);
        add(isLast);
        add(remove);

        name.setEditable(false);
        phi.setEditable(false);

        updatePanelFromIon(new Ion(nm));
    }

    /**
     * Copies the parameters from the Ion to the graphical JPanel.
     */
    public void updatePanelFromIon(Ion i) {
        name.setText(i.getName());
        charge.setValue(new Double(i.getCharge()));
        mu.setValue(new Double(i.getMu()));
        cdadc.setText(i.getCdadc());
        phi.setValue(new Double(i.getPhi() / 1000.0 / Nav));
        c0.setValue(new Double(i.getC0() / 1000.0 / Nav));
        cL.setValue(new Double(i.getCL() / 1000.0 / Nav));
        phi.setEditable(false);
        revalidate();
    }

    /**
     * Copies the parameters from the graphical JPanel to the Ion
     */
    public void updateIonFromPanel(Ion i) {
        i.setName(name.getText());
        i.setCharge((int) ((Number) charge.getValue()).doubleValue());
        i.setMu(((Number) mu.getValue()).doubleValue());
        i.setCdadc(cdadc.getText());
        i.setPhi(((Number) phi.getValue()).doubleValue() * 1000.0 * Nav);
        i.setC0(((Number) c0.getValue()).doubleValue() * 1000.0 * Nav);
        i.setCL(((Number) cL.getValue()).doubleValue() * 1000.0 * Nav);
    }

    /**
     * Updates the editability of the text fields.
     */
    public void updateEditable() {
        c0.setEditable(!isLast.isSelected());
        cL.setEditable(!isLast.isSelected());
        repaint();
    }

}