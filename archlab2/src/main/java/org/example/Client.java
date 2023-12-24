package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 1234;
    private static BufferedWriter out;

    public static String inputFileName(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Клиент подключен. Введите текст или путь к файлу \n-->");
        String path = sc.next();
        return path;

    }
    public static void sendFile(File _file, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        FileInputStream fileInputStream = new FileInputStream(_file);
        //для чтения байтов из файла.
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);//0-индекс начала записи
        }
        fileInputStream.close();
    }
    //"C:\Users\regis\IdeaProjects\archlab2\22.jpg"

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, PORT);
        OutputStream outputStream = socket.getOutputStream();

        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String str = inputFileName();
        try {
            File file = new File(str);
            sendFile(file, outputStream);
            System.out.println("Изображение отправлено на сервер.");
        } catch (Exception ex){
            out.write(str + "\n");
            out.flush();
        }
        outputStream.close();
        socket.close();



    }


}

