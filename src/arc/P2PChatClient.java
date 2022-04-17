package arc;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2PChatClient {
  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);

    // チャットルームを作るかどうかを選択する
    System.out.println("チャットルームを作る: 0\nチャットルームに参加する: 1\n入力してください:");
    int selected;
    while (true) {
      try {
        selected = scanner.nextInt();
        if (selected == 0 || selected == 1) {
          break;
        }
      } catch (Exception e) {
        System.out.println("正しい値を入力してください:");
      }
    }

    // ポート番号の入力
    System.out.println("接続するポート番号を入力してください:");
    int port = 0;
    while (true) {
      try {
        port = scanner.nextInt();
        break;
      } catch (Exception e) {
        System.out.println("正しい値を入力してください:");
      }
    }

    // ソケットの準備
    Socket socket;
    ServerSocket serverSocket = null;

    if (selected == 0) {
      // チャットルームを作る
      serverSocket = new ServerSocket(port);
      System.out.println("ユーザーの参加待ちです...");
      socket = serverSocket.accept();
      System.out.println("接続できました！ " + socket);
    } else {
      // チャットルームに参加する
      InetAddress addr = InetAddress.getByName("localhost");
      // ソケットの接続を試みる
      try {
        socket = new Socket(addr, port);
        System.out.println("接続できました！ " + socket);
      } catch (IOException e) {
        System.out.println("接続できませんでした。そのポート番号が使われているか確認してください。");
        scanner.close();
        return;
      }
    }

    // 入力ストリームの作成
    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
    BufferedReader in = new BufferedReader(inputStreamReader);

    // 出力ストリームの作成
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
    PrintWriter out = new PrintWriter(bufferedWriter, true);

    // 読み込み&書き込み用スレッドの作成
    MessageOutputStreamThread messageOutputStreamThread = new MessageOutputStreamThread(in);
    MessageInputStreamThread messageInputStreamThread = new MessageInputStreamThread(out, scanner);

    // スレッド実行
    messageOutputStreamThread.start();
    messageInputStreamThread.start();

    // スレッドの終了待ち
    try {
      messageOutputStreamThread.join();
      messageInputStreamThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // ソケットの切断
    System.out.println("通信を終了します");
    socket.close();
    scanner.close();
    if (serverSocket != null) {
      serverSocket.close();
    }
  }
}

class MessageInputStreamThread extends Thread {
  private PrintWriter out;
  private Scanner scanner;

  MessageInputStreamThread(PrintWriter out, Scanner scanner) {
    this.out = out;
    this.scanner = scanner;
  }

  public void run() {
    while (true) {
      // クライアント側の入力待ち
      String message = scanner.next();
      // メッセージ送信
      out.println(message);
      if (message.equals("bye")) {
        break;
      }
    }
  }
}

class MessageOutputStreamThread extends Thread {
  private BufferedReader in;

  MessageOutputStreamThread(BufferedReader in) {
    this.in = in;
  }

  public void run() {
    while (true) {
      String message;
      try {
        message = in.readLine();
        System.out.println("相手: " + message);
        if (message.equals("bye")) {
          break;
        }
      } catch (IOException e) {
        break;
      }
    }
  }
}
