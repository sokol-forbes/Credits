package by.bsuir.app;

import by.bsuir.app.util.constants.ConstantsMSG;
import by.bsuir.app.util.Server;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static by.bsuir.app.util.constants.ConstantsMSG.*;


@Log4j2
public class ServerRunner {

    private static boolean isOn = true;
    private static Thread serverThread = null;
    private static final AtomicInteger countOfConnected = new AtomicInteger(0);


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int answer;
        String answerString = null;
        showMenu();

        while (isOn) {

            try {
                answerString = scanner.nextLine();
                answer = Integer.parseInt(answerString);

                switch (answer) {
                    case 1 -> {
                        Server server = new Server();
                        serverThread = new Thread(server);
                        serverThread.start();
                        try {
                            serverThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    case 2 -> log.info(CURRENT_CONNECTION_MSG + getCountOfConnected());
//                    case 3 -> {
//                        isOn = false;
//                        Server.setIsActive(false);
//                        try {
//                            if (serverThread != null)
//                                serverThread.join();
//                            log.error(SERVER_SWITCHED_OFF_MSG);
//                        } catch (InterruptedException e) {
//                            log.error(SERVER_SWITCHED_OFF_ERROR_MSG);
//                            e.printStackTrace();
//                        }
//                    }
                    default -> throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(INCORRECT_VALUE_RU_MSG + answerString);
            }
        }
    }

    private static void showMenu () {
        int i = 0;
        System.out.println(++i + ConstantsMSG.DELIMITER_MSG + ConstantsMSG.MENU_1_MSG);
        System.out.println(++i + ConstantsMSG.DELIMITER_MSG + ConstantsMSG.MENU_2_MSG);
//        System.out.println(++i + ConstantsMSG.DELIMITER_MSG + ConstantsMSG.MENU_3_MSG);
        System.out.print(MENU_CHOOSE_MSG);
    }

    public synchronized static void decrementCountOfConnected() {
        log.info(CURRENT_CONNECTION_MSG + countOfConnected.decrementAndGet());
    }

    public synchronized static void incrementCountOfConnected() {
        log.info(CURRENT_CONNECTION_MSG + countOfConnected.incrementAndGet());
    }

    public synchronized static int getCountOfConnected() {
        return countOfConnected.get();
    }
}
