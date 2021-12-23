package by.bsuir.app.util.connection;

import by.bsuir.app.exception.GettingDataException;
import by.bsuir.app.util.Commands;
import by.bsuir.app.util.Status;
import by.bsuir.app.util.constants.Constants;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static by.bsuir.app.util.constants.Constants.DELIMITER_MSG;
import static by.bsuir.app.util.constants.Constants.GETTING_DATA_FAILURE;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Phone {
    static Socket socket;
    static ObjectInputStream ois;
    static ObjectOutputStream oos;

    public Phone(Socket s) throws IOException {
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            socket = s;
    }

    public static void send(String str) throws IOException {
        oos.writeUTF(str);
        oos.flush();
    }

    public static void sendObject(Object obj) throws IOException {
        oos.writeObject(obj);
        oos.flush();
    }

    public static Object readObject() throws ClassNotFoundException, IOException {
        return ois.readObject();
    }

    public static String read() throws IOException {
        return ois.readUTF();
    }

    public static void shutdown() throws IOException {
        ois.close();
        oos.close();
        socket.close();
    }

    public static Socket getSocket() {
        return socket;
    }

    public static Object sendOrGetData(Commands command, Object obj) throws IOException, ClassNotFoundException,
            GettingDataException {
        String sendCommand = command.toString();
        Object sendObj = obj;

        Phone.send(sendCommand);
        Phone.sendObject(sendObj);

        log.info(Constants.REQUEST_MSG + sendCommand + DELIMITER_MSG + sendObj);

        String response = Phone.read();
        if (!response.equals(Status.OK.toString()))
            throw new IOException();

        Object responseObj = Phone.readObject();
        log.info(Constants.RESPONSE_MSG + response + DELIMITER_MSG + responseObj);

        if (responseObj == null)
            throw new GettingDataException(GETTING_DATA_FAILURE);

        return responseObj;
    }
}
