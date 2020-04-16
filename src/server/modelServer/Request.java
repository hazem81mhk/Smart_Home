package server.modelServer;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private User sender;
    private ArrayList<User> reciverList;
    private String textMessage;
    private ImageIcon imageMessage;
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

    public ImageIcon getImageMessage() {
        return imageMessage;
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
