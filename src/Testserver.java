import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Testserver {
    public static void main(String[] args) throws Exception {
        System.out.println("MY IP address is " + InetAddress.getLocalHost().getHostAddress());
        ServerSocket server     = new ServerSocket(4031);
        Socket socket           = server.accept();
        System.out.println("Yay somebody connected! " + socket);
        socket.close();
        server.close();
    }
}