package server.model;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private ImageIcon photo;

    public User(String name, ImageIcon photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getPhoto() {
        return photo;
    }
}
