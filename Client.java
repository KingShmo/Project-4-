import java.io.*;
import java.net.Socket;


/**
 * Client class
 * <p>
 * Clients used for testing. It runs two threads.
 *
 * @author Zuhair Almansouri, lab sec L16
 * @version December 11, 2021
 */

public class Client extends Thread {

    /**
     * Synchronize
     */
    private static Object sync = new Object();

    //Invoke the client method
    public static void main(String[] args) throws IOException {

        client();

    }

    //Run two client threads
    public static void client() {


        Client[] clients = new Client[]{new Client(), new Client()};

        for (Client client : clients) {
            client.start();
        }


    }

    //Connect to the server
    public void run() {

        try {

            Socket socket = new Socket("localhost", 1234);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}