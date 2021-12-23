package by.bsuir.app.util.connection;

import by.bsuir.app.util.constants.ConstantsMSG;
import by.bsuir.app.util.constants.Status;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Socket;

import static by.bsuir.app.ServerRunner.decrementCountOfConnected;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientHandler extends Thread {
    final Phone phone;
    final Socket s;
    volatile boolean keepRunning;

    public ClientHandler(ThreadGroup group, String name, Socket socket) throws IOException {
        super(group, name);
        s = socket;
        keepRunning = true;
        phone = new Phone(socket);
    }

    @Override
    public void run() {
        String requestAction = null;
        Object requestObject = null;
        while (keepRunning) {
            try {
                requestAction = null;
                requestObject = null;

                requestAction = phone.read();
                requestObject = phone.readObject();
                log.info(ConstantsMSG.REQUEST_MSG + this.getName() + " - " + requestAction + " - " + requestObject);

                Object response = null;
                Status responseStatus = Status.OK;

                if (Commands.valueOf(requestAction) == Commands.CLOSE_CONNECTION) {
                    keepRunning = false;
                    response = Status.CLOSE_CONNECTION.name();
                } else {
                    response = CommandHandler.execute(Commands.valueOf(requestAction), requestObject);
                    if (response == null)
                        responseStatus = Status.REQUEST_ERROR;
                }

                phone.send(responseStatus.toString());

                if (responseStatus == Status.OK) {
                    phone.sendObject(response);
                }

                log.info(ConstantsMSG.RESPONSE_MSG + this.getName() + " - " + responseStatus + " - " + response);

            } catch (IOException | ClassNotFoundException e) {
                log.error(e.getMessage());
                //e.printStackTrace();
                keepRunning = false;
                decrementCountOfConnected();
            }
        }
        try {
            phone.shutdown();
        } catch (IOException e) {
            log.info(e.getMessage());
            //e.printStackTrace();
        }
    }
}

//TODO Заменить на COMMAND

