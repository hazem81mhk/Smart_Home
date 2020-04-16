package client.model;

import server.modelServer.Request;
import server.modelServer.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 16/04/2020
 *
 * @Mohammed Amayri
 */

public class Client extends Thread {
    private int port;
    private InetAddress ip;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream ous;

    public Client(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
        start();
    }

    @Override
    public void run() {
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ip, port);
            ous = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("We have a problem connecting to the server");
        }

        try {
            while (true) {
                Object object = ois.readObject();
                if (object != null) {
                    sort(object);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sort(Object object) {

        if (object instanceof Request) {
            Request request = (Request) object;
            System.out.println(request.getTextMessage());
        }
    }

    public void sendRequest(String request) {
        Request req = new Request(request);
        try {
            ous.writeObject(req);
            ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUser(User user) {
        try {
            ous.writeObject(user);
            ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


