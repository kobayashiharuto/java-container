package recordtest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RecordTest {
  public static void main(String[] args) throws Exception {
    final Recorder recorder = new Recorder(new File("result/RecordAudio.wav"));
    Thread stopper = new Thread(recorder);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("enter to start recording >>");
    in.readLine();
    System.out.println("recording...");
    stopper.start();
    System.out.print("enter to stop recording >>");
    in.readLine();
    recorder.stopRecording();
    System.out.println("finished");
  }
}
