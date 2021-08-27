
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	private static Clip chompClip;

	public static void beginningSound() {
		Clip music;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("res/sounds/pacman_beginning.wav"));
			music = AudioSystem.getClip();
			music.open(stream);
			music.start();
		} catch (Exception e) {
			e.printStackTrace();
			music = null;
		}
	}

	public static void chompSound() {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("res/sounds/pacman_chomp.wav"));
			chompClip = AudioSystem.getClip();
			chompClip.open(stream);
			chompClip.loop(Clip.LOOP_CONTINUOUSLY);
			chompClip.start();
		} catch (Exception e) {
			e.printStackTrace();
			chompClip = null;
		}
	}

	public static void stopChompSound() {
		try {
			chompClip.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deathSound() {
		Clip music;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("res/sounds/pacman_death.wav"));
			music = AudioSystem.getClip();
			music.open(stream);
			music.start();
		} catch (Exception e) {
			e.printStackTrace();
			music = null;
		}
	}

	public static void eatSound() {
		Clip music;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("res/sounds/pacman_eat.wav"));
			music = AudioSystem.getClip();
			music.open(stream);
			music.start();
		} catch (Exception e) {
			e.printStackTrace();
			music = null;
		}
	}

	public static void gameoverSound() {
		Clip music;
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("res/sounds/gameover.wav"));
			music = AudioSystem.getClip();
			music.open(stream);
			music.start();
		} catch (Exception e) {
			e.printStackTrace();
			music = null;
		}
	}
}