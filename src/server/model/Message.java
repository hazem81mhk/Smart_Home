package server.model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private User sender;
    private ArrayList<User> reciverList;
    private String textMessage;
    private ImageIcon imageMessage;
    private String sendTime;
    private String reciveTime;

    public Message(ArrayList<User> reciverList, User sender, String textMessage,
                   ImageIcon imageMessage, String sendTime, String reciveTime) {
        this.sender = sender;
        this.reciverList = reciverList;
        this.textMessage = textMessage;
        this.imageMessage = imageMessage;
        this.sendTime = sendTime;
        this.reciveTime = reciveTime;
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
