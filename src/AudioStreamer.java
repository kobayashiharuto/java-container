import java.io.*;
import java.net.*;
import record.*;

public class AudioStreamer {
  static public int PORT = 8080;

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(PORT); // ソケットを作成する
    Socket socket = serverSocket.accept(); // コネクション設定要求を待つ
    System.out.println("接続しました: " + socket);

    File soundFile = AudioUtil.getSoundFile(args[0]);

    System.out.println("server: " + soundFile);

    try (FileInputStream in = new FileInputStream(soundFile)) {
      OutputStream out = socket.getOutputStream();

      byte buffer[] = new byte[2048];
      int count;
      while ((count = in.read(buffer)) != -1)
        out.write(buffer, 0, count);
    }

    serverSocket.close();
    System.out.println("終了します");
  }

  static void record(OutputStream outputStream) throws Exception {
    final Recorder recorder = new Recorder(outputStream);
    Thread stopper = new Thread(recorder);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("エンターで録音開始");
    in.readLine();
    System.out.println("録音中...");
    stopper.start();
    System.out.print("エンターで録音終了");
    in.readLine();
    recorder.stopRecording();
    System.out.println("finished");
  }
}
