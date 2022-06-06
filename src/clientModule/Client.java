package clientModule;

import clientModule.exceptions.WrongInputException;
import common.util.*;
import clientModule.util.Console;
import common.util.response.Response;
import common.util.response.ResponseBody;
import common.util.response.ResponseCode;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Client {
    private final static String PATH = System.getenv().get("sixth_project");
    private Console console;
    private final String HOST;
    private final int PORT;
    private SocketChannel socketChannel;
    private SocketAddress address;
    boolean flag = true;
    private final int BUFFERSIZE = 2048;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFERSIZE);
    final byte[] FINISHSYMB = {(byte) 'F'};
    private User user;



    public Client(String host, int port, Console console) {
        this.HOST = host;
        this.PORT = port;
        this.console = console;
    }

    public void run() {
        try {
            socketChannel = SocketChannel.open();
            address = new InetSocketAddress("localhost", this.PORT);
            socketChannel.connect(address);
            System.out.println("Соединение с сервером установлено");
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                try {
                    requestToServer = serverResponse != null ? console.interactiveMode(serverResponse.getResponseCode(), user) :
                            console.interactiveMode(null, user);
                    if (requestToServer.isEmpty()) continue;
                    if (requestToServer.getCommandName().equals("exit")) {
                        flag = false;
                        break;
                    }
                    if (flag) {
                        makeByteBufferToRequest(requestToServer);
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                        int package_number = socketChannel.read(byteBuffer);
                        byteBuffer.clear();
                        int received = 0;
                        while (package_number != received) {
                            socketChannel.read(byteBuffer);
                            received++;
                            TimeUnit.MILLISECONDS.sleep(5);
                        }
                        serverResponse = deserialize();
                        if (serverResponse.getResponseCode().equals(ResponseCode.OK) && (requestToServer.getCommandName().equals("sign_in") || requestToServer.getCommandName().equals("sign_up")))
                            user = requestToServer.getUser();
                        if (serverResponse.getResponseCode().equals(ResponseCode.OK) && requestToServer.getCommandName().equals("log_out"))
                            user = null;
                        ResponseBody responseBody = serverResponse.getResponseBody();

                        System.out.print(responseBody.getMessage());
                    }
                } catch (NullPointerException ignored) {
                }
            } while (!requestToServer.getCommandName().equals("exit") && flag);
        } catch (IOException | ClassNotFoundException | WrongInputException | InterruptedException exception) {
            System.out.println("Произошла ошибка при работе с сервером!" + exception);
        }
        try {
            if (!flag) {
                socketChannel.close();
            }
        } catch (IOException ignored) {
        }
    }


    private void makeByteBufferToRequest(Request request) throws IOException {
        byteBuffer.put(serialize(request));
        byteBuffer.flip();
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return response;
    }


}
