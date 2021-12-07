import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class MultiApps {

    /**
     * Clients currently connected
     */
    static ArrayList<String> clients = new ArrayList<>();

    /**
     * First launch to read files from the database
     */
    static boolean initialLaunch = true;

    /**
     * Clients counter
     */
    private static int counter = 0;


    public static void main(String[] args) throws Exception {

        try {
            ToCompare.main();
        } catch (SocketException e) {
            System.out.println("User logged out.");
        } catch (NumberFormatException e) {
            System.out.println("User logged out.");
        } catch (Exception e) {
            System.out.println("User logged out.");
        }

    }

    /**
     * Creates a client
     * @throws IOException
     */
    public static void initializeClient() throws IOException {

        //Client.client();
        clients.add("Client " + ++counter);

    }

    /**
     * Displays all clients
     */
    public static void viewClients() {

        for (int i = 0; i < clients.size(); i++)
            System.out.println(clients.get(i));

    }


}
