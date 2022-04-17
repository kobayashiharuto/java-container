package record;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

public class Recorder implements Runnable {
  static final long RECORD_TIME = 100; // 0.1 sec
  // File wavFile;
  OutputStream outputStream;
  AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
  static final float SAMPLE_RATE = 44100;
  static final int SAMPLE_SIZE_IN_BITS = 16;
  static final int CHANNELS = 2;
  static final boolean SIGNED = true;
  static final boolean BIG_ENDIAN = true;
  TargetDataLine line;

  public Recorder(OutputStream outputStream) throws Exception {
    AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    this.outputStream = outputStream;
    line = AudioSystem.getTargetDataLine(format);
    line.open(format);
  }

  @Override
  public void run() {
    // startRecording();
    testSender();
  }

  private void startRecording() {
    try {
      line.start();
      AudioInputStream ais = new AudioInputStream(line);
      AudioSystem.write(ais, fileType, outputStream);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void testSender() {
    try {
      AudioInputStream ain = testPlay("C:/Users/owner/Desktop/大学関連/実験/ソフトウェア製作/java/src/record/Recorder.java");
      if (ain != null) {
        AudioSystem.write(ain, AudioFileFormat.Type.WAVE, outputStream);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public static AudioInputStream testPlay(String filename) {
    AudioInputStream din = null;
    try {
      File file = new File(filename);
      AudioInputStream in = AudioSystem.getAudioInputStream(file);
      System.out.println("Before :: " + in.available());

      AudioFormat baseFormat = in.getFormat();
      AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, baseFormat.getSampleRate(),
          8, baseFormat.getChannels(), baseFormat.getChannels(),
          baseFormat.getSampleRate(), false);
      din = AudioSystem.getAudioInputStream(decodedFormat, in);
      System.out.println("After :: " + din.available());
      return din;
    } catch (Exception e) {
      // Handle exception.
      e.printStackTrace();
    }
    return din;
  }

  public void stopRecording() {
    line.stop();
    line.close();
  }
}