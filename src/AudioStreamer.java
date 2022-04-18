import javax.sound.sampled.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AudioStreamer {
  public static void main(String[] args) throws Exception {
    AudioFormat format = FormatData.format;
    TargetDataLine line = AudioSystem.getTargetDataLine(format);
    byte[] data = new byte[line.getBufferSize() / 5];

    DatagramSocket socket = new DatagramSocket();
    InetAddress address = InetAddress.getLocalHost();

    try {
      line.open(format);
      line.start();

      while (true) {
        line.read(data, 0, data.length);
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, AudioListener.PORT);
        socket.send(sendPacket);
      }

    } finally {
      line.close();
      socket.close();
    }
  }
}