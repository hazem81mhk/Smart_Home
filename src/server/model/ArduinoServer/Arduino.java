package server.model.ArduinoServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri and Moahmmed Hazem Kudaimi
 */

public class Arduino {
    OutputStream ous;

    public Arduino(OutputStream ous) {
        this.ous = ous;
    }

    public void sendToArduino(String str) {
        BufferedWriter outPut = new BufferedWriter(new OutputStreamWriter(ous));
        try {
            outPut.write(str);
            outPut.newLine();
            outPut.flush();
        } catch (IOException e) {
            System.out.println("The Arduino is disconnected");
        }
    }
}
