import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MainServer {

    public static final int SERVER_PORT = 50001;

    public static void main(String[] args) {
        try (final ServerSocket server = new ServerSocket(SERVER_PORT);
        ) {
            int number_of_clients = 1;
            while (true) {
                final Socket clientConnection = server.accept();
                final ThreadReader threadReader = new ThreadReader(
                        clientConnection.getInputStream(),
                        "Client"+number_of_clients
                );
                threadReader.start();
                System.out.printf("Client"+number_of_clients+" was connected\n");
                ++number_of_clients;
            }
        } catch (SocketException e) {
            System.out.println("Client disconnected");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}