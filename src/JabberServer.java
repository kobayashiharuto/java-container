import java.io.*;
import java.net.*;

public class JabberServer {
  public static void main(String[] args) throws IOException {
    int port = 0;
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.println("正しいポート番号を入力してください。");
      return;
    }

    ServerSocket s = new ServerSocket(port); // ソケットを作成する
    System.out.println("Started: " + s);
    try {
      Socket socket = s.accept(); // コネクション設定要求を待つ
      try {
        System.out.println("Connection accepted: " + socket);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(
                socket.getInputStream())); // データ受信用バッファの設定
        PrintWriter out = new PrintWriter(
            new BufferedWriter(
                new OutputStreamWriter(
                    socket.getOutputStream())),
            true); // 送信バッファ設定
        while (true) {
          String str = in.readLine(); // データの受信
          if (str.equals("END"))
            break;
          System.out.println("Echoing : ");
          out.println(str); // データの送信
        }
      } finally {
        System.out.println("closing...");
        socket.close();
      }
    } finally {
      s.close();
    }
  }
}
