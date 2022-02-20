import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.applet.*;
import java.text.*;
import java.util.*;

/** A JPanel for editing the properties of an IonSet. */

public class IonSetPanel extends JPanel {

	// Physical costants
	private static final double KT = 1.3806488e-23 * (25.0 + 273.15);
	private static final double e = 1.6e-19;
	private static final double Nav = 6.02e23;
	private static final double epsilon = 8.854187817e-12 * 80.1;

	// Grafica
	private ButtonGroup bgX = new ButtonGroup();
	private ButtonGroup bgLast = new ButtonGroup();

	private ArrayList<IonPanel> list = new ArrayList<IonPanel>();

	/**
	 * Creates the IonSetPanel, a graphical element for handling an IonSet.
	 */
	public IonSetPanel() {

		setSize(800, 300);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel titles = new JPanel();
		titles.setLayout(new GridLayout(2, 10));

		titles.add(new JLabel("Ion"));
		titles.add(new JLabel(""));
		titles.add(new JLabel("Mobility"));
		titles.add(new JLabel(""));
		titles.add(new JLabel("Flux"));
		titles.add(new JLabel("c0"));
		titles.add(new JLabel("cL"));
		titles.add(new JLabel("X"));
		titles.add(new JLabel("Last"));
		titles.add(new JLabel(""));

		titles.add(new JLabel("Name"));
		titles.add(new JLabel("Charge"));
		titles.add(new JLabel("(m/s / N)"));
		titles.add(new JLabel("cdadc"));
		titles.add(new JLabel("(frac)"));
		titles.add(new JLabel("(M)"));
		titles.add(new JLabel("(M)"));
		titles.add(new JLabel(""));
		titles.add(new JLabel(""));
		titles.add(new JLabel(""));

		add(titles);
	}

	private class UpdateEditableActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int j = 0; j < list.size(); j++)
				list.get(j).updateEditable();
		}
	}

	private class RemoveActionListener implements ActionListener {
		private IonPanel rri;

		public RemoveActionListener(IonPanel ri) {
			rri = ri;
		}

		public void actionPerformed(ActionEvent e) {
			removeIonPanel(rri);
		}
	}

	/**
	 * Add an IonPanel to the IonSetPanel.
	 * 
	 * @param i the IonPanel to be added.
	 */
	public void addIonPanel(IonPanel i) {
		bgX.add(i.isX);
		bgLast.add(i.isLast);
		i.isX.addActionListener(new UpdateEditableActionListener());
		i.isLast.addActionListener(new UpdateEditableActionListener());
		add(i);
		i.setBorder(new EmptyBorder(5, 0, 0, 0));
		list.add(i);
		i.remove.addActionListener(new RemoveActionListener(i));
		repaint();
	}

	/**
	 * Remove an IonPanel. Mainly useful for graphical interfaces.
	 * 
	 * @param i the IonPanel to be removed.
	 */
	public void removeIonPanel(IonPanel i) {
		bgX.remove(i.isX);
		bgLast.remove(i.isLast);
		remove(i);
		list.remove(i);
		repaint();
	}

	/**
	 * Creates an IonSet from the data in the graphical panel.
	 * 
	 * @return the generated IonSet
	 */
	public IonSet getIonSet() throws Exception {
		// At least two ions
		if (list.size() < 2)
			throw new Exception("Please define at least two ions.");

		// Checks the isX and isLast buttons
		boolean f = false;
		for (int j = 0; j < list.size(); j++)
			f |= list.get(j).isLast.isSelected();
		if (!f) {
			int j = list.size() - 1;
			if (list.get(j).isX.isSelected())
				j--;
			list.get(j).isLast.setSelected(true);
		}
		f = false;
		for (int j = 0; j < list.size(); j++)
			f |= list.get(j).isX.isSelected();
		if (!f) {
			int j = list.size() - 1;
			if (list.get(j).isLast.isSelected())
				j--;
			list.get(j).isX.setSelected(true);
		}
		for (int j = 0; j < list.size(); j++)
			list.get(j).updateEditable();
		for (int j = 0; j < list.size(); j++)
			if (list.get(j).isX.isSelected() && list.get(j).isLast.isSelected())
				throw new Exception("Please select different ions as \"X\" and \"Last\"");

		// Creates the IonSet
		boolean nonzero0 = false;
		boolean nonzeroL = false;
		IonSet is = new IonSet();
		Ion iLast = null, iX = null;
		for (int j = 0; j < list.size(); j++) {
			Ion i = new Ion(" ");
			list.get(j).updateIonFromPanel(i);
			nonzero0 |= i.getC0() > 0.0;
			nonzeroL |= i.getCL() > 0.0;
			if (list.get(j).isX.isSelected())
				iX = i;
			else if (list.get(j).isLast.isSelected())
				iLast = i;
			else
				is.add(i);
		}
		if (!nonzero0)
			throw new Exception("All the concentrations are zero at x=0. It is impossible to perform the calculation.");
		if (!nonzeroL)
			throw new Exception("All the concentrations are zero at x=L. It is impossible to perform the calculation.");
		if ((iX.getCL() - iX.getC0()) / 1000.0 == 0.0)
			throw new Exception(
					"The concentrations c0 and cL must be different for the ion set as \"X\", called " + iX);
		is.add(iX);
		is.add(iLast);
		return is;
	}

	/**
	 * Creates an IonSet from the data in the graphical panel.
	 * 
	 * @return the generated IonSet
	 */
	public void setIonSet(IonSet is) throws Exception {
		assert list.size() == is.size();
		int jX = -1, jLast = -1;
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).isX.isSelected())
				jX = j;
			if (list.get(j).isLast.isSelected())
				jLast = j;
		}
		int j = 0;
		for (int k = 0; k < is.size() - 2; k++) {
			while ((j == jX) || (j == jLast))
				j++;
			list.get(j).updatePanelFromIon(is.get(k));
			j++;
		}
		list.get(jX).updatePanelFromIon(is.get(is.size() - 2));
		list.get(jLast).updatePanelFromIon(is.get(is.size() - 1));
	}

}
