import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/** A graphical panel for interacting with the IonSet. */

public class LJPPanel extends JPanel {

	private IonSetPanel is;
	private JTextField ni;
	private JButton nb;
	private JButton calc;
	private JTextField vi;
	private JTextArea err;

	private Container toBeRepainted = null;

	/**
	 * Creates a graphical panel for interacting with an IonSetPanel.
	 * 
	 * @param isss the IonSetPanel.
	 */
	public LJPPanel(IonSetPanel isss, Container c) {
		is = isss;
		toBeRepainted = c;

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		JPanel nip = new JPanel();
		JPanel nipin = new JPanel();
		nip.add(new JLabel("Name: "));
		ni = new JTextField();
		ni.setColumns(6);
		nip.add(ni);
		nb = new JButton("Add");
		nip.add(nb);
		nip.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add Ion"));
		nipin.add(nip);
		nipin.setMaximumSize(nipin.getPreferredSize());
		add(nipin, gbc);

		JPanel vip = new JPanel();
		JPanel vipin = new JPanel();
		calc = new JButton("Calculate");
		vip.add(calc);
		vi = new JTextField();
		vi.setColumns(6);
		vi.setEditable(false);
		vip.add(vi);
		vip.add(new JLabel("mV"));
		vip.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Liquid Junction Potential"));
		vipin.add(vip);
		vipin.setMaximumSize(vipin.getPreferredSize());
		add(vipin, gbc);

		JPanel errPin = new JPanel();
		err = new JTextArea();
		err.setLineWrap(true);
		err.setWrapStyleWord(true);
		err.setEditable(false);
		JScrollPane errScroll = new JScrollPane(err);
		errScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		errScroll.setPreferredSize(new Dimension(250, 50));
		JPanel errP = new JPanel();
		errP.add(errScroll);
		errP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Error Messages"));
		errPin.add(errP);
		errPin.setMaximumSize(errPin.getPreferredSize());
		add(errPin, gbc);

		JPanel isin = new JPanel();
		isin.add(is);
		add(isss, gbc);

		class Calculation implements Runnable {
			public void run() {
				err.setText("");
				vi.setText("");
				try {
					IonSet isv = is.getIonSet();
					String result = ("" + isv.calculate() * 1000.0);
					if (result.length() > 6)
						result = result.substring(0, 6);
					vi.setText(result);
					is.setIonSet(isv);
				} catch (Exception ex) {
					err.setText("" + ex);
				}
				calc.setText("Calculate");
			}
		}

		calc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (calc.getText().equals("Calculate")) {
					calc.setText("Abort");
					(new Thread(new Calculation())).start();
				} else {
					Solver.stop();
				}
			}
		});

		ActionListener niac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ni.getText().length() == 0)
					return;
				is.addIonPanel(new IonPanel(ni.getText()));
				ni.setText("");
				is.revalidate();
				revalidate();
				if (toBeRepainted != null)
					toBeRepainted.validate();
			}
		};
		nb.addActionListener(niac);
		ni.addActionListener(niac);

	}

}