package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetSocketAddress;
import javax.imageio.ImageIO;

public class ImageServer {

    public static void start() throws IOException {
        // Создаем HTTP-сервер на порту 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Установка обработчика для пути "/upload"
        server.createContext("/upload", new ImageHandler());

        // Установка обработчика для пути "/index"
        server.createContext("/index", new IndexHandler());

        server.start();
        System.out.println("Сервер запущен на порту 8080");
    }

    static class ImageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            File imageFile = new File("image.png");
            BufferedImage image = ImageIO.read(imageFile);

            // Отправка изображение в ответ на запрос
            exchange.sendResponseHeaders(200, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                ImageIO.write(image, "png", os);
            }
        }
    }

    static class IndexHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Собираем HTML-страницу
            String htmlResponse =   "<html>" +
                                        "<body>" +
                                             "<img src=\"/upload\" alt=\"Uploaded Image\">" +
                                        "</body>" +
                                    "</html>";

            // Отправьте страничку на запрос
            exchange.sendResponseHeaders(200, htmlResponse.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(htmlResponse.getBytes());
            }
        }
    }
}