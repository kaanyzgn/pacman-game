	import javax.swing.JFrame;

	public class Launcher extends JFrame {

		

		public Launcher() {
			add(new Game());
		}

		public static void main(String[] args) {
			Launcher pac = new Launcher();
			pac.setVisible(true);
			pac.setTitle("Pacman");
			pac.setSize(720, 800);
			pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
			pac.setLocationRelativeTo(null);
		}
	}