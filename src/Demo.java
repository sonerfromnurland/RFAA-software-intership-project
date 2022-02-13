import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Demo
{
    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public Demo(InetAddress address, int port) throws IOException
    {

        if(! isReachable(address.toString(), port, 1500)) {
            System.err.println("No connection!");
            throw new IOException("The IP" + " (" + address + ") " + "cannot be reachable!");
        }

        try {
            setSocket(new Socket(address, port));
            setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            setWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            getSocket().setSoTimeout(1500);
        } catch (SocketException e) {
            System.err.println("Host is down!");
        } catch (SocketTimeoutException e) {
            System.err.println("Connection Timeout!");
        }

    }

    public void send(String msg) throws IOException
    {
        getWriter().write(msg, 0, msg.length());
        getWriter().flush();
    }

    public String recv() throws IOException
    {
        return getReader().readLine();
    }

    private static boolean isReachable(String addr, int openPort, int timeOutMillis) {
        // Any Open port on other machine
        // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        String cleanIp = addr;
        if (addr.contains("/")) cleanIp = addr.substring(1);

        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(cleanIp, openPort), timeOutMillis);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static void main(String[] args)
    {

        StringBuilder cmd;
        Scanner scan= new Scanner(System.in);
        //boolean flag = true;
        String ipAddr = "10.0.0.32";
        int port = 4001;
        boolean checkConnection;
        boolean flag = true;


        try {
            InetAddress host = InetAddress.getByName(ipAddr);

            if(! isReachable(ipAddr, port, 1500)) {
                System.err.println("No connection!");
                throw new IOException("The IP" + " (" + ipAddr + ") " + "cannot be reachable!");
            }

            Demo client = new Demo(host, port);
            if (! client.getSocket().isConnected())
                throw new IOException("The IP with the port" + " (" + ipAddr + " - "
                        + port + ") " + " cannot be connectable!");
            else {
                System.out.println("Connection OK!");
            }

            while (client.getSocket().isConnected() && !client.getSocket().isClosed()) {
                String text = scan.nextLine();
                cmd = new StringBuilder();
                cmd.append(text + "\r\n");
                client.send(cmd.toString());
                String response = client.recv();
                System.out.println("Client received: " + response);

            }

            client.getSocket().close();
        }
        catch (IOException e) {
            System.out.println("Caught Exception: " + e.toString());
        }
    }
}