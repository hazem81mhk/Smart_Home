/*
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
       // server.sendTrafficUser(logStr);
    }

    public void userIsOffline(User user) {
        String time = simpleDateFormat.format(new Date());
        String logStr = time + "    " + user.getName() + " is Offline!";
       // server.sendTrafficUser(logStr);
    }
}
*/
