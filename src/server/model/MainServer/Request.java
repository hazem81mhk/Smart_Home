package server.model.MainServer;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri
 */

public class Request implements Serializable {
    private User sender;
    private ArrayList<User> reciverList;
    private String textMessage;
    private String sendTime;
    private String reciveTime;

    public Request(String textMessage) {
        this.textMessage = textMessage;
    }

    public User getSender() {
        return sender;
    }

    public ArrayList<User> getReciverList() {
        return reciverList;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReciveTime() {
        return reciveTime;
    }

    public void setReciveTime(String reciveTime) {
        this.reciveTime = reciveTime;
    }
}
