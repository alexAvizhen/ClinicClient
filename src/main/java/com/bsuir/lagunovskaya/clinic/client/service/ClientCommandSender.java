package com.bsuir.lagunovskaya.clinic.client.service;

import com.bsuir.lagunovskaya.clinic.client.ClinicClient;
import com.bsuir.lagunovskaya.clinic.communication.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientCommandSender {

    public static ServerResponse sendClientCommand(ClientCommand clientCommand) {
        Socket clientSocket = null;
        // мы узнаем что хочет сказать клиент?
        ObjectInputStream in = null; // поток чтения из сокета
        ObjectOutputStream out = null; // поток записи в сокет
        ServerResponse serverResponse = null;
        try {
            // создаём сокет общения на стороне клиента в конструкторе объекта
            // адрес - локальный хост, порт - 4004, такой же как у сервера
            clientSocket = new Socket("localhost", Integer.valueOf(ClinicClient.properties.getProperty("server.port")));
            try {
                // писать туда же
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                // читать соообщения с сервера
                in = new ObjectInputStream(clientSocket.getInputStream());



                out.writeObject(clientCommand); // отправляем сообщение на сервер
                out.flush();
                serverResponse = (ServerResponse) in.readObject(); // ждём, что скажет сервер
                System.out.println(serverResponse); // получив - выводим на экран
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Запрос обработан...");
                try {
                    clientSocket.close();
                    in.close();
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverResponse;
    }

}
