package client.model;

import java.io.Serializable;

public class OurObject implements Serializable {
    String name;
    public OurObject(String name)
    {
        this.name=name;
    }
}
