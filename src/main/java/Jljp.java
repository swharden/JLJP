import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Graphical interface for the Jljp classes. */

public class Jljp extends JFrame {

	public Jljp() {
		this.setTitle(String.format("JLJP by Doriano Brogioli (Version %s)", Version.getString()));
		setSize(900, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LJPPanel ljpPanel = new LJPPanel(new IonSetPanel(), null);
		ljpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(ljpPanel, BorderLayout.PAGE_START);
	}

	public static void main(String[] args) {
		Jljp j = new Jljp();
		j.setVisible(true);
	}

}