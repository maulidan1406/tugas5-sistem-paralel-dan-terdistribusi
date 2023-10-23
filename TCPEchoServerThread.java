import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServerThread {

    private static ServerSocket servSock;
    private static final int PORT = 12345;

    public TCPEchoServerThread() {
    }

    public void start() {
        try {
            servSock = new ServerSocket(PORT);

            while (true) {
                Socket clientSocket = servSock.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Menutup koneksi....");
                servSock.close();
            } catch (IOException e) {
                System.out.println("Tidak dapat memutuskan koneksi");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        TCPEchoServerThread es = new TCPEchoServerThread();
        System.out.println("Server telah berjalan di komputer ini pada port " + PORT);
        es.start();
    }
}