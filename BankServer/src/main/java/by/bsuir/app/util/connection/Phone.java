package by.bsuir.app.util.connection;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.*;
import java.net.Socket;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Phone {
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public Phone(Socket s) {
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String str) throws IOException {
        oos.writeUTF(str);
        oos.flush();
    }

    public void sendObject(Object obj) throws IOException {
        oos.writeObject(obj);
        oos.flush();
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        return ois.readObject();
    }

    public String read() throws IOException {
        return ois.readUTF();
    }

    public void shutdown() throws IOException {
        ois.close();
        oos.close();
    }

}
