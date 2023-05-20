import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class MainClient1 {
    public static void main(String[] args) {
        try (final Socket clientSocket = new Socket("localhost", 50001);
             final Scanner scanner = new Scanner(System.in);
             final ThreadReader threadReader = new ThreadReader(
                     clientSocket.getInputStream(),
                     "Client"
             )
        ) {
            threadReader.start();

            final DataOutput clientOutput = new DataOutputStream(clientSocket.getOutputStream());
            String clientData;

            do {
                clientData = scanner.nextLine();
                clientOutput.writeBytes(clientData + "\n");
            } while (!clientData.equals("exit"));
        } catch (SocketException e) {
            System.out.println("Server is closed");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}