import java.awt.event.*;

public class CloseWindow extends WindowAdapter {

	public void windowClosing(WindowEvent e) {
	
		System.exit(0);
	
	}

}