package org.example;
import java.io.*;
import java.net.*;


public class Server {
    private static ImageServer imageServer = new ImageServer();
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен. Ожидание подключения клиента...");

        Socket socket = serverSocket.accept();
        System.out.println("Клиент подключен.");

        InputStream inputStream = socket.getInputStream();
        BufferedInputStream in = new BufferedInputStream(inputStream);
        FileOutputStream fileOutputStream = new FileOutputStream("image.png");

        String word = "";

        byte[] contents = new byte[1024];
        int bytesRead1 = 0;
        while((bytesRead1 = in.read(contents)) != -1) {
            word += new String(contents, 0, bytesRead1);
            fileOutputStream.write(contents, 0, bytesRead1);
        }



        System.out.print(word);
        fileOutputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();

        System.out.println("Изображение получено и сохранено.");
        imageServer.start();

    }
}
