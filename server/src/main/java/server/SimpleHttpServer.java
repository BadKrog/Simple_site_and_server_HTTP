package server;

import java.io.IOException;
import java.net.ServerSocket;

public class SimpleHttpServer {
    public static void main(String[] args) {
        try{
            ServerSocket serverConnect = new ServerSocket(8080);
            System.out.println("Сокет создан на порту 8080 - ждем запросов от клиентов");

            // Начинаем слушать запросы
            while (true){
                ClientHandler myServer = new ClientHandler(serverConnect.accept());

                System.out.println("Соединение установлено.");

                // Создаем отдельный поток для обработки запроса формирования ответа
                Thread thread = new Thread(myServer);
                thread.start();
                Thread.sleep(100);
            }
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
            System.out.println("Соединение потеряно.");
        }
    }
}
