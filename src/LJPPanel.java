import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/** A graphical panel for interacting with the IonSet.*/

public class LJPPanel extends JPanel {

    private IonSetPanel is;
    private JTextField ni;
    private JButton nb;
    private JButton calc;
    private JTextField vi;
    private JTextArea err;

    private Container toBeRepainted = null;

    /** Creates a graphical panel for interacting with an IonSetPanel.
     @param isss the IonSetPanel.*/
    public LJPPanel(IonSetPanel isss, Container c) {
	is=isss;
	toBeRepainted=c;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	JPanel nip=new JPanel();
	JPanel nipin=new JPanel();
	nip.add(new JLabel("Ion name: "));
	ni=new JTextField();
	ni.setColumns(6);
	nip.add(ni);
	nb=new JButton("Add");
	nip.add(nb);
	nip.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	nipin.add(nip);
	add(nipin);
	
	JPanel vip=new JPanel();
	JPanel vipin=new JPanel();
	calc=new JButton("Calculate");
	vip.add(calc);
	vip.add(new JLabel(" Voltage (mV): "));
	vi=new JTextField();
	vi.setColumns(6);
	vi.setEditable(false);
	vip.add(vi);
	vip.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	vipin.add(vip);
	add(vipin);
	
	JPanel errP=new JPanel();
	JPanel errPin=new JPanel();
	errP.add(new JLabel("Messages:"));

	err = new JTextArea();
	err.setLineWrap(true);
	err.setWrapStyleWord(true);
	err.setEditable(false);
	JScrollPane errScroll = new JScrollPane(err);
	errScroll.setVerticalScrollBarPolicy(
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	errScroll.setPreferredSize(new Dimension(250, 50));

	errP.add(errScroll);
	errP.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	errPin.add(errP);
	add(errPin);

	JPanel isin=new JPanel();
	isin.add(is);
	add(isin);

	class Calculation implements Runnable {
	    public void run() { 
		err.setText("");
		vi.setText("");
		try {
		    IonSet isv= is.getIonSet();
		    String result=(""+isv.calculate()*1000.0);
		    if (result.length()>6) result=result.substring(0,6);
		    vi.setText(result);
		    is.setIonSet(isv);
		} catch (Exception ex) {
		    err.setText(""+ex);
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
	
	ActionListener niac=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    if (ni.getText().length()==0) return;
		    is.addIonPanel(new IonPanel(ni.getText()));
		    ni.setText("");	    
		    is.revalidate();
		    revalidate();
		    if (toBeRepainted!=null)
			toBeRepainted.validate();
		}
	    };
	nb.addActionListener(niac);
	ni.addActionListener(niac);

    }

}