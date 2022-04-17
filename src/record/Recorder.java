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
  File wavFile;
  // OutputStream outputStream;
  AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
  static final float SAMPLE_RATE = 44100;
  static final int SAMPLE_SIZE_IN_BITS = 16;
  static final int CHANNELS = 2;
  static final boolean SIGNED = true;
  static final boolean BIG_ENDIAN = true;
  TargetDataLine line;

  public Recorder(File wavFile) throws Exception {
    AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    this.wavFile = wavFile;
    line = AudioSystem.getTargetDataLine(format);
    line.open(format);
  }

  @Override
  public void run() {
    startRecording();
  }

  private void startRecording() {
    try {
      line.start();
      AudioInputStream ais = new AudioInputStream(line);
      AudioSystem.write(ais, fileType, wavFile);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void stopRecording() {
    line.stop();
    line.close();
  }
}