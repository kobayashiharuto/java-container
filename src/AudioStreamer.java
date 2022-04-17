import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AudioStreamer {
  public static void main(String[] args) throws Exception {
    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
    float rate = 44100.0f;
    int sampleSize = 16;
    int channels = 2;
    boolean bigEndian = true;

    AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
        * channels, rate, bigEndian);

    TargetDataLine line;
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

    DatagramSocket socket = new DatagramSocket();
    InetAddress IPAddress = InetAddress.getLocalHost();

    try {
      line = (TargetDataLine) AudioSystem.getLine(info);
      line.open(format);

      byte[] data = new byte[line.getBufferSize() / 5];

      line.start();
      while (true) {
        line.read(data, 0, data.length);
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 8080);
        socket.send(sendPacket);
      }

    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } finally {
      socket.close();
    }
  }
}