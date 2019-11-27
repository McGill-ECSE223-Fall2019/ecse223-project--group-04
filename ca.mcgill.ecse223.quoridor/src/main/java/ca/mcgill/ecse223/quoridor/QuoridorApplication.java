package ca.mcgill.ecse223.quoridor;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.*;


public class QuoridorApplication {

	private static Quoridor quoridor;

	public static void main() {
		try {
			MainMenu menu = new MainMenu();
			menu.frame.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
 		return quoridor;
	}

}
