import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	private String path;
	private long clipTime;
	private Clip clip;

	public Music(String path) {
		this.path = path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void play() {
		try {
			File f = new File("songs/mysong.wav");
			InputStream in = new FileInputStream(path);
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(in);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception exception) {
			System.out.println("Failed To Play The WAV File!");
		}
	}

	public void pause() {
		clipTime = clip.getLongFramePosition();
		clip.close();
	}

	public void resume() {
		clip.setMicrosecondPosition(clipTime);
		clip.start();
	}
}
