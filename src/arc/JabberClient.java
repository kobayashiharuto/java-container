package arc;
import java.io.*;
import java.net.*;

public class JabberClient {
  public static void main(String[] args) throws IOException {
    int port = 0;
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.println("正しいポート番号を入力してください。");
      return;
    }
    InetAddress addr = InetAddress.getByName("localhost"); // IP アドレスへの変換
    System.out.println("addr = " + addr);
    Socket socket = new Socket(addr, port); // ソケットの生成
    try {
      System.out.println("socket = " + socket);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(
              socket.getInputStream())); // データ受信用バッファの設定
      PrintWriter out = new PrintWriter(
          new BufferedWriter(
              new OutputStreamWriter(
                  socket.getOutputStream())),
          true); // 送信バッファ設定
      for (int i = 0; i < 10; i++) {
        out.println("howdy " + i); // データ送信
        String str = in.readLine(); // データ受信
        System.out.println(str);
      }
      out.println("END");
    } finally {
      System.out.println("closing...");
      socket.close();
    }
  }
}
