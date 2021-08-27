
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyManager extends KeyAdapter {

	Game p;

	public KeyManager(Game p) {
		this.p = p;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A ) {
			p.dx = -1;
			p.dy = 0;
			p.pacman.setLastPosition(2);
		} else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			p.dx = 1;
			p.dy = 0;
			p.pacman.setLastPosition(0);
		} else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			p.dx = 0;
			p.dy = -1;
			p.pacman.setLastPosition(3);
		} else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			p.dx = 0;
			p.dy = 1;
			p.pacman.setLastPosition(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			p.dx = 0;
			p.dy = 0;
		} else if (key == KeyEvent.VK_RIGHT) {
			p.dx = 0;
			p.dy = 0;
		} else if (key == KeyEvent.VK_UP) {
			p.dx = 0;
			p.dy = 0;
		} else if (key == KeyEvent.VK_DOWN) {
			p.dx = 0;
			p.dy = 0;
		}
	}
}