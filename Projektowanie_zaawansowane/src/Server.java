import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 6767;
    private static final int MAX_CLIENTS = 2;

    private static Map<String, Object> dataMap = new HashMap<>();
    private static int activeClients = 0;

    public static void main(String[] args) {
        initializeData();

        System.out.println("Serwer uruchomiony na porcie " + PORT);
        System.out.println("Maksymalna liczba klientow: " + MAX_CLIENTS);
        System.out.println();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeData() {
        for (int i = 1; i <= 4; i++) {
            dataMap.put("Laptop_" + i, new Laptop("Dell_" + i, 8 + i));
            dataMap.put("Telefon_" + i, new Telefon("Samsung_" + i, 6.0 + i * 0.1));
            dataMap.put("Ksiazka_" + i, new Ksiazka("Java_" + i, "Autor_" + i));
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            int clientId = -1;
            boolean accepted = false;

            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                Thread.sleep((int)(Math.random() * 1000 + 500));

                clientId = in.readInt();

                synchronized (Server.class) {
                    if (activeClients >= MAX_CLIENTS) {
                        System.out.println("[ODRZUCONO] Klient ID: " + clientId);
                        out.writeObject("REFUSED");
                        out.flush();
                        return;
                    } else {
                        activeClients++;
                        accepted = true;
                        System.out.println("[ZAAKCEPTOWANO] Klient ID: " + clientId);
                        out.writeObject("OK");
                        out.flush();
                    }
                }

                while (true) {
                    String requestedClass = (String) in.readObject();

                    if ("KONIEC".equals(requestedClass)) {
                        break;
                    }

                    Thread.sleep((int)(Math.random() * 500 + 200));

                    List<Object> result = new ArrayList<>();
                    for (String key : dataMap.keySet()) {
                        if (key.startsWith(requestedClass + "_")) {
                            result.add(dataMap.get(key));
                        }
                    }

                    if (!result.isEmpty()) {
                        out.writeObject(result);
                        System.out.println("[WYSLANO] " + result.size() +
                                " obiektow '" + requestedClass + "' do ID: " + clientId);
                    } else {
                        out.writeObject("Brak obiektow klasy: " + requestedClass);
                        System.out.println("[BRAK] Wysylam pulapke do ID: " + clientId);
                    }
                    out.flush();
                }

                System.out.println("[ROZLACZONO] Klient ID: " + clientId);

            } catch (Exception e) {
                System.err.println("Blad klienta ID: " + clientId);
            } finally {
                if (accepted) {
                    synchronized (Server.class) {
                        activeClients--;
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}