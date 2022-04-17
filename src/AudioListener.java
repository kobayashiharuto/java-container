import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AudioListener {
  public static void main(String[] args) throws Exception {
    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
    float rate = 44100.0f;
    int sampleSize = 16;
    int channels = 2;
    boolean bigEndian = true;

    AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
        * channels, rate, bigEndian);

    SourceDataLine speakers;
    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);

    speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
    speakers.open(format);

    DatagramSocket socket = new DatagramSocket(8080);
    byte[] data = new byte[speakers.getBufferSize() / 5];

    try {
      speakers.start();
      while (true) {
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
        socket.receive(receivePacket);
        speakers.write(data, 0, data.length);
      }
    } finally {
      socket.close();
    }
  }
}