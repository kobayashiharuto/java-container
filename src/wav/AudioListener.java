package wav;
import java.io.*;
import java.net.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioListener {
  public static void main(String[] args) throws Exception {
    InetAddress addr = InetAddress.getByName("localhost"); // IP アドレスへの変換
    System.out.println("addr = " + addr);

    Socket socket = new Socket(addr, AudioStreamer.PORT); // ソケットの生成
    try {
      while (true) {
        InputStream in = new BufferedInputStream(socket.getInputStream());
        play(in);
      }
    } finally {
      socket.close();
      System.out.println("通信を終了します");
    }
  }

  private static void play(final InputStream in) throws Exception {
    System.out.println("受信待ち");
    AudioInputStream ais = AudioSystem.getAudioInputStream(in);
    try (Clip clip = AudioSystem.getClip()) {
      System.out.println("再生開始");
      clip.open(ais);
      clip.start();
      Thread.sleep(2000); // given clip.drain a chance to start
      System.out.println("再生終了");
    }
  }
}