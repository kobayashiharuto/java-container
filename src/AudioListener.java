import java.io.*;
import java.net.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioListener {
  public static void main(String[] args) throws Exception {
    InetAddress addr = InetAddress.getByName("localhost"); // IP アドレスへの変換
    System.out.println("addr = " + addr);

    if (args.length > 0) {
      File soundFile = AudioUtil.getSoundFile(args[0]);
      System.out.println("Client: " + soundFile);
      try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(soundFile))) {
        play(in);
      }
    } else {
      Socket socket = new Socket(addr, AudioStreamer.PORT); // ソケットの生成
      try {
        System.out.println("socket = " + socket);
        InputStream in = new BufferedInputStream(socket.getInputStream());
        play(in);
      } finally {
        socket.close();
        System.out.println("通信を終了します");

      }
    }

  }

  private static synchronized void play(final InputStream in) throws Exception {
    AudioInputStream ais = AudioSystem.getAudioInputStream(in);
    try (Clip clip = AudioSystem.getClip()) {
      clip.open(ais);
      clip.start();
      Thread.sleep(100); // given clip.drain a chance to start
      clip.drain();
    }
  }
}