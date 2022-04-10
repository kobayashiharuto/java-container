import java.io.*;
import java.net.*;

public class ChatServer {
  static public int PORT = 8080;

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(PORT); // ソケットを作成する
    System.out.println("Started: " + serverSocket);
    Socket socket = serverSocket.accept(); // コネクション設定要求を待つ
    System.out.println("Accepted: " + socket);

    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
    BufferedReader in = new BufferedReader(inputStreamReader); // データ受信用バッファの設定

    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter); // データ送信用バッファの設定
    PrintWriter out = new PrintWriter(bufferedWriter, true); // データ送信用printWriterの設定

    try {
      while (true) {
        String str = in.readLine(); // データの受信
        if (str.equals("END")) {
          break;
        }
        System.out.println("接続しているユーザーに送信します: " + str);
        out.println(str); // データの送信
      }
    } finally {
      System.out.println("サーバーを終了します");
      socket.close();
      serverSocket.close();
    }
  }
}
