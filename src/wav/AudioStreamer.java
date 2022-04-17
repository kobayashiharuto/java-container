package wav;

import java.io.*;
import java.net.*;

import record.*;

public class AudioStreamer {
  static public int PORT = 8080;
  static private String tempPath = "temp/temp.wav";

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(PORT); // ソケットを作成する
    Socket socket = serverSocket.accept(); // コネクション設定要求を待つ
    System.out.println("接続しました: " + socket);

    try {
      OutputStream out = socket.getOutputStream();
      while (true) {
        record();
        File soundFile = AudioUtil.getSoundFile(tempPath);
        FileInputStream in = new FileInputStream(soundFile);

        byte buffer[] = new byte[2048];
        int bufferSize;
        while ((bufferSize = in.read(buffer)) != -1) {
          out.write(buffer, 0, bufferSize);
        }
        in.close();
      }
    } finally {
      socket.close();
      serverSocket.close();
      System.out.println("終了します");
    }
  }

  static void record() throws Exception {
    final Recorder recorder = new Recorder(new File(tempPath));
    Thread stopper = new Thread(recorder);
    System.out.println("recording...");
    stopper.start();
    Thread.sleep(2000);
    recorder.stopRecording();
    System.out.println("send");
  }
}
