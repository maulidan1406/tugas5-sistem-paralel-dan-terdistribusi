import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static int numConnections;
    private int connectionId;
    private Socket link;

    public ClientHandler(Socket clientSocket) {
        connectionId = numConnections++;
        System.out.println("Melayani koneksi ke-" + connectionId);
        link = clientSocket;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        int numMessages = 0;

        try {
            out = new PrintWriter(link.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            String message = in.readLine();

            while (message != null && !message.equals("close")) {
                System.out.println("Pesan diterima: [" + message + "] dari client " + connectionId + " dalam " + message.length() + " bytes");
                numMessages++;
                out.println("Isi Pesan " + numMessages + ": " + message);
                message = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (link != null) link.close();
                System.out.println("Menutup koneksi, #" + connectionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = "Hello, server!";
            out.println(message);
            System.out.println("Pesan terkirim: [" + message + "]");

            String response = in.readLine();
            System.out.println("Pesan diterima: [" + response + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}