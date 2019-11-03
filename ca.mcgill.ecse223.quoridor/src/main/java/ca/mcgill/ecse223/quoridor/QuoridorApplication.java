package ca.mcgill.ecse223.quoridor;


import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.*;

public class QuoridorApplication {

	private static Quoridor quoridor;

	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SavePositionPage.main(args);
            }
        });
	}
	
	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
 		return quoridor;
	}

}
