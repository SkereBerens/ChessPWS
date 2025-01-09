package view;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.io.File;

public class SoundEffect {
	static Clip clip;
	
	public static void SetFile(String soundFileName) {
		
		try {
			File file = new File(soundFileName);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(sound);
		} catch (Exception e) {
			System.out.println("sad");
		}
	}
	
	public static void play() {
		clip.setFramePosition(0);
		clip.start();
	}
}
