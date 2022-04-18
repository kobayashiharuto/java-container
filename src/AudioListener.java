import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class AudioListener {
  static int PORT = 8080;

  public static void main(String[] args) throws Exception {
    AudioFormat format = FormatData.format;
    SourceDataLine speakers = AudioSystem.getSourceDataLine(format);

    speakers.open(format);

    DatagramSocket socket = new DatagramSocket(PORT);
    byte[] data = new byte[speakers.getBufferSize() / 5];

    try {
      speakers.start();
      while (true) {
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
        socket.receive(receivePacket);
        speakers.write(data, 0, data.length);
      }
    } finally {
      speakers.close();
      socket.close();
    }
  }
}