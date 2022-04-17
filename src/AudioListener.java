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
        System.out.println("受信待ち");
        InputStream in = new BufferedInputStream(socket.getInputStream());
        System.out.println("再生開始");
        play(in);
        System.out.println("再生終了");
      }
    } finally {
      socket.close();
      System.out.println("通信を終了します");
    }
  }

  private static synchronized void play(final InputStream in) throws Exception {
    AudioInputStream ais = AudioSystem.getAudioInputStream(in);
    try (Clip clip = AudioSystem.getClip()) {
      System.out.println("count: aaaa");
      clip.open(ais);
      clip.start();
      Thread.sleep(100); // given clip.drain a chance to start
      clip.drain();
    }
  }
}