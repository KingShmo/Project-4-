import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements Runnable {


    /**
     * Client's name
     */
    private String clientName = "Zuhair";

    /**
     * Synchronize
     */
    private static Object sync = new Object();

    /**
     * Count threads
     */
    static int counter = 0;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        client();

    }

    public static void client() {

        Thread thread = new Thread(new Client());

        thread.start();

    }

    private String getClientName() {
        return clientName;
    }

    public void run() {



            try {
                Socket socket = new Socket("localhost", 1234);

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.flush();

                //System.out.println("Thread " + ++counter);


                do {
                    boolean b = true;
                    String incomingInfo = br.readLine();
                    do {
                        System.out.println(incomingInfo);
                        incomingInfo = br.readLine();
                        b = incomingInfo.equals("esc");
                    } while (!b);


                    String response = JOptionPane.showInputDialog(null, "Your input:", "Application",
                                        JOptionPane.QUESTION_MESSAGE);
                    if (response == null)
                        break;

                    pw.println(response);
                    pw.flush();


                } while (true);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }



    }




}


