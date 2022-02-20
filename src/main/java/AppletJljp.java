import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.applet.*;
import java.text.*;

/** Java liquid junction potential calculator as an applet */

public class AppletJljp extends Applet {

	public void init() {
		setSize(1000, 600);
		IonSetPanel is = new IonSetPanel();
		LJPPanel p = new LJPPanel(is, this);
		add(p);
	}

}