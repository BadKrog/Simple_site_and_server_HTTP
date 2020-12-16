package server;

import server.logics.Logics;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class ClientHandler implements Runnable {
    static final String DEFAULT_FILE = "index.html";

    private static Socket clientDialog;

    public ClientHandler(Socket client) {
        clientDialog = client;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;



        if (clientDialog.isClosed()){System.out.println("Сокет закрыт"); return;}
        try {
            // Канал чтения из сокета
            in = new BufferedReader(new InputStreamReader(clientDialog.getInputStream()));
            // канал записи в сокет (для HEADER)
            out = new PrintWriter(clientDialog.getOutputStream());
            // канал записи в сокет (для данных)
            dataOut = new BufferedOutputStream(clientDialog.getOutputStream());

            System.out.println("1");
            // Первая строка запроса
            String input = in.readLine();

            if(input==null){
                System.out.println("Сокет закрыт");
                clientDialog.close();
                return;
            }

            // Разбираем запрос по токенам
            StringTokenizer parse = new StringTokenizer(input);
            // Получаем HTTP метод от клиента
            String method = parse.nextToken().toUpperCase();
            // Текст запроса от клиента
            fileRequested = parse.nextToken();

            System.out.println("Stroka: " + input);
            System.out.println("Method: " + method);
            System.out.println("Request: " + fileRequested);

//------------------------ POST ---------------------------------------------
            // Прописываем POST
            if (method.equals("POST")) {
                System.out.println("Зашли в пост");
                byte[] fileData = "".getBytes();

                if(fileRequested.equals("/task")) {
                    String line;
                    // Пропускаем ненужную информацию
                    while ((line = in.readLine()) != null && !(line.isEmpty())) {
                    }
                    line = in.readLine();

                    fileData = Logics.getBody(line).getBytes();
                }

                if(fileRequested.equals("/delete")){
                    DataManager bd = DataManager.getInstance();

                    String line;

                    // Пропускаем ненужную информацию
                    while ((line = in.readLine()) != null && !(line.isEmpty())) {
                    }
                    line = in.readLine();
                    System.out.println(line);

                    bd.Delete(line);
                }

                if(fileRequested.equals("/add")) {
                    DataManager bd = DataManager.getInstance();

                    String id;
                    // Пропускаем ненужную информацию
                    while ((id = in.readLine()) != null && !(id.isEmpty())) {
                    }
                    id = in.readLine();
                    String name = in.readLine();
                    String rating = in.readLine();
                    String time = in.readLine();

                    System.out.println(id+" "+name+" "+rating+" "+time);
                    bd.Add(id, name, rating, time);
                }

                if(fileRequested.equals("/edit")) {
                    DataManager bd = DataManager.getInstance();

                    String id;
                    // Пропускаем ненужную информацию
                    while ((id = in.readLine()) != null && !(id.isEmpty())) {
                    }
                    id = in.readLine();
                    String name = in.readLine();
                    String rating = in.readLine();
                    String time = in.readLine();

                    System.out.println(id+" "+name+" "+rating+" "+time);

                    bd.Editing(id, name, rating, time);
                }

                if(fileRequested.equals("/load")) {
                    DataManager bd = DataManager.getInstance();

                    fileData = bd.toString().getBytes();

                    System.out.println("Зугрузили список:\n"+bd.toString());
                }
                // Шлем HTTP Headers
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server : 1.0");
                out.println("Date: " + new Date());
                out.println("Content-type: text/plain");
                //out.println("Transfer-Encoding: chunked");
                out.println("Connection: keep-alive");
                out.println("Vary: Accept-Encoding");

                // Длина ответа - эхо запроса без первого /
                out.println("Content-length: " + fileData.length);
                out.println();
                out.flush();

                dataOut.write(fileData, 0, fileData.length);

                System.out.println("Ответ отослан");
                dataOut.flush();
            }

//------------------------ GET ---------------------------------------------
            // Прописываем GET
            if (method.equals("GET")) {

                // По умолчанию использовать index.html
                if (fileRequested.endsWith("/")) {
                    fileRequested += DEFAULT_FILE;
                }

                byte[] fileData = null;

                try {
                    fileData = readFileData(fileRequested);
                }
                catch (FileNotFoundException e){
                    fileRequested = "/"+DEFAULT_FILE;
                    fileData = readFileData(fileRequested);
                    System.err.println("Ошибка в имени файла, вывожу default." + e.getMessage());
                }

                String content = getContentType(fileRequested);

                // Шлем HTTP Headers
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server : 1.0");
                out.println("Date: " + new Date());
                out.println("Content-type: " + content);
                //out.println("Transfer-Encoding: chunked");
                out.println("Connection: keep-alive");
                out.println("Vary: Accept-Encoding");

                // Длина ответа - эхо запроса без первого /
                out.println("Content-length: " + fileData.length);
                out.println();
                out.flush();

                dataOut.write(fileData, 0, fileData.length);

                System.out.println("Ответ отослан");
                dataOut.flush();
            }


        } catch (IOException e) {
            System.err.println("Input exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientDialog.close();
                in.close();
                out.close();
                dataOut.close();
                System.out.println("Вышел из потока");
            } catch (Exception e) {
                System.err.println("Error closing stream: " + e.getMessage());
            }
        }

    }

    private boolean isHtml(String fileRequested) {
        return (fileRequested.endsWith(".htm") || fileRequested.endsWith(".html"));
    }

    private boolean isCss(String fileRequested){
        return fileRequested.endsWith(".css");
    }

    private String getContentType(String fileRequested) {
        if(isHtml(fileRequested)){
            return "text/html; charset=UTF-8";
        }
        else if (isCss(fileRequested)){
            return "text/css;";
        }
        else return "text/plain";
    }

    private byte[] readFileData(String filePath) throws IOException {
        InputStream fileIn = null;
        int fileLength;
        byte[] fileData;

        try {
            fileIn = getClass().getResourceAsStream(filePath);
            if (null == fileIn) {
                throw new FileNotFoundException();
            }
            fileLength = fileIn.available();
            fileData = new byte[fileLength];
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return fileData;
    }

}
