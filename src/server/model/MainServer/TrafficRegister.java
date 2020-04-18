package server.model.MainServer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficRegister {
    private SimpleDateFormat simpleDateFormat;
    private Server server;

    public TrafficRegister(Server server2) {
        this.server = server2;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public void userIsOnline(User user) {
        String time = simpleDateFormat.format(new Date());
        String logStr = time + "    " + user.getName() + " is Online!";
        server.sendTrafficUser(logStr);
    }

    public void userIsOffline(User user) {
        String time = simpleDateFormat.format(new Date());
        String logStr = time + "    " + user.getName() + " is Offline!";
        server.sendTrafficUser(logStr);
    }

    public void msgOffline(String receiver, String sender, String sendTime) {
        String logStr = sendTime + "    is send to Offline user. Receiver is: " + receiver + ", Sender is: " + sender;
        server.sendTrafficMessage(logStr);

    }

    public void msgOnline(String reciver, String sender, String sendTime, String received) {
        String logStr = sendTime + "    is send, " + received + " is received.  Receiver is: " + reciver + ", Sender is: " + sender;
        server.sendTrafficMessage(logStr);
    }

    public void msgOnlineSend(String reciver, String sender, String sendTime, String received) {
        String logStr = received + "    message is deliver.  " + sendTime + " was send, Receiver is: " + reciver + ", Sender is: " + sender;
        server.sendTrafficMessage(logStr);
    }
}
