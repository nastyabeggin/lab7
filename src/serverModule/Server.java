package serverModule;

import common.util.Request;
import common.util.response.Response;
import serverModule.util.CommandManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Scanner;

public class Server {
    private int port;
    private CommandManager CommandManager;
    private Selector selector;
    private Scanner scanner;
    private int numRead = -1;
    private int previous;
    private Response response;
    private boolean flag = true;
    private SocketAddress address;
    private ServerSocketChannel serverSocketChannel;
    private final int BUFFERSIZE = 2048;
    final byte[] FINISHSYMB = {(byte) 'F'};
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFERSIZE); // выделение памяти под буфер

    public Server(int port, CommandManager CommandManager) throws IOException {
        this.port = port;
        this.CommandManager = CommandManager;
        selector = Selector.open();
        address = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(address);
        selector = SelectorProvider.provider().openSelector();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        checkInput();
    }

    private void closeServer() throws IOException {
        this.selector.close();
        this.serverSocketChannel.close();
    }

    private void checkInput() {
        scanner = new Scanner(System.in);
        Runnable CommandManagerInput = () -> {
            try {
                while (flag) {
                    String[] CommandManagerCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    CommandManagerCommand[1] = CommandManagerCommand[1].trim();
                    if (!CommandManagerCommand[0].equals("save") && !CommandManagerCommand[0].equals("exit")) {
                        System.out.println("Сервер не может сам принимать такую команду!");
                    }
                    if (CommandManagerCommand[0].equals("exit")) {
                        System.out.println("Сервер заканчивает работу!");
                        serverSocketChannel.close();
                        closeServer();
                        this.flag = false;
                    }
                    if (CommandManagerCommand[0].equals("save")) {
                        this.CommandManager.saveCollection();
                    }
                }
            } catch (Exception ignored) {
            }
        };
        Thread thread = new Thread(CommandManagerInput);
        thread.start();
    }

    public void run() throws IOException {
        System.out.println("Сервер запущен!");
        while (flag) {
            try {
                //TODO Requests
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();
                    if (!key.isValid())
                        continue;
                    if (key.isAcceptable()) {
                        accept(key);
                    }
                    if (key.isReadable())
                        read(key);
                    if (key.isWritable())
                        write(key);
                }
            } catch (ClosedSelectorException ignored) {
            }
        }
    }


    // Метод, реализующий получение нового подключения
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) {
        try {
            Request request;
            SocketChannel socketChannel = (SocketChannel) key.channel();
            numRead = socketChannel.read(readBuffer);
            previous = readBuffer.position();
            ByteArrayInputStream bArrayIS = new ByteArrayInputStream(readBuffer.array());
            ObjectInputStream ois = new ObjectInputStream(bArrayIS);
            request = (Request) ois.readObject();
            String commandName = request.getCommandName();
            System.out.println("Получена команда: " + commandName);
            bArrayIS.close();
            ois.close();
            this.response = executeRequest(request);
            System.out.println(response);
            System.out.println("Выполнена команда: " + commandName);
            readBuffer.clear();
            SelectionKey selectionKey = socketChannel.keyFor(selector);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            selector.wakeup();

        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println(ignored);
        }
    }

    private void write(SelectionKey key) throws IOException {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            byte[] buffer = byteArrayOutputStream.toByteArray();
            objectOutputStream.flush();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            objectOutputStream.close();
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            if (byteBuffer.array().length <= BUFFERSIZE) {
                socketChannel.write(ByteBuffer.allocateDirect(1));
                socketChannel.write(ByteBuffer.wrap(buffer));

            } else {
                int limit = byteBuffer.limit();
                int size = (int) (Math.ceil((double) limit / (double) BUFFERSIZE));
                ByteBuffer[] ret = new ByteBuffer[size];
                int srcIndex = 0;
                socketChannel.write(ByteBuffer.allocateDirect(size));
                for (int i = 0; i < size; i++) {
                    int bufferSize = BUFFERSIZE;
                    if (i == size - 1) {
                        bufferSize = byteBuffer.limit() % BUFFERSIZE;
                    }
                    byte[] dest = new byte[bufferSize];
                    System.arraycopy(byteBuffer.array(), srcIndex, dest, 0, dest.length);
                    srcIndex = srcIndex + bufferSize;
                    ret[i] = ByteBuffer.wrap(dest);
                    ret[i].position(0);
                    ret[i].limit(ret[i].capacity());
                    socketChannel.write(ret[i]);
                }
            }
            System.out.println("check");
            SelectionKey selectionKey = socketChannel.keyFor(selector);
            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (Exception ignored) {

        }
    }

    private Response executeRequest(Request request) {
        return CommandManager.manage(request);
    }

}
