import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
  public static void main(String[] args) throws IOException {
    InetAddress addr = InetAddress.getByName("localhost"); // IP アドレスへの変換
    System.out.println("addr = " + addr);

    Socket socket = new Socket(addr, ChatServer.PORT); // ソケットの生成
    System.out.println("socket = " + socket);

    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
    BufferedReader in = new BufferedReader(inputStreamReader); // データ受信用バッファの設定

    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter); // データ送信用バッファの設定
    PrintWriter out = new PrintWriter(bufferedWriter, true); // データ送信用printWriterの設定
    Scanner scanner = new Scanner(System.in);

    try {
      while (true) {
        // クライアント側の入力待ち
        // System.out.println("メッセージを入力してください");
        String message = scanner.next();

        // メッセージ送信
        // System.out.println("送信します: " + message);
        out.println(message);

        //
        // String str = in.readLine(); // データ受信
        // System.out.println(str);
      }
    } finally {
      System.out.println("通信を終了します");
      socket.close();
      scanner.close();
    }
  }
}
