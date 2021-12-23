package by.bsuir.app.util;

import by.bsuir.app.util.connection.ClientHandler;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static by.bsuir.app.ServerRunner.*;
import static by.bsuir.app.util.constants.ConstantsMSG.*;

@Log4j2
public class Server implements Runnable {

    private static volatile boolean isActive = true;
    private static final ThreadGroup threadGroup = new ThreadGroup("mainGroup");
    private static final PropertyReader p = new PropertyReader();

    @Override
    public void run() {


        int local_port = p.getPropertyInt("server.port");

        while (isActive) {

            try (ServerSocket ss = new ServerSocket(local_port)) {
                log.info(SERVER_STARTED_MSG);
                log.info(CURRENT_PORT_MSG + local_port);

                Socket socket;
                while (isActive) {
                    socket = null;
                    socket = ss.accept();
                    incrementCountOfConnected();

                    log.info(CLIENT_CONNECTED_MSG + socket + " " + CURRENT_CONNECTION_MSG + getCountOfConnected());

                    Runnable runnable = new ClientHandler(threadGroup, "Client_" + getCountOfConnected(), socket);

                    Thread newThread = new Thread(runnable);
                    newThread.start();

                }
                do {
                    log.info(SERVER_OFF_MSG + getCountOfConnected());
                } while (threadGroup.activeCount() > 0);
            } catch (BindException e) {
                log.error(e.getMessage() + " " + CURRENT_PORT_MSG + local_port);
                ++local_port;
            } catch (SocketException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                decrementCountOfConnected();
            }
            //threadGroup.interrupt();
        }
    }

    public static boolean isIsActive() {
        return isActive;
    }

    public static void setIsActive(boolean isActive) {
        Server.isActive = isActive;
    }
}
