import javax.sound.sampled.AudioFormat;

public class FormatData {
  private static final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
  private static final float rate = 44100.0f;
  private static final int sampleSize = 16;
  private static final int channels = 2;
  private static final boolean bigEndian = true;

  public static final AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
      * channels, rate, bigEndian);
}
