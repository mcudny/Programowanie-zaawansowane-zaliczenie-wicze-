import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 6767;

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(() -> runClient()).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    public static void runClient() {
        int id = 1000 + new Random().nextInt(9000);

        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeInt(id);
            out.flush();

            String status = (String) in.readObject();
            System.out.println("Klient " + id + " status: " + status);

            if ("REFUSED".equals(status)) {
                System.out.println("[ODRZUCONO] Klient " + id);
                return;
            }

            System.out.println("[ZAAKCEPTOWANO] Klient " + id);

            String[] requests = {"Laptop", "Telefon", "Ksiazka", "NieznanaKlasa"};

            for (String req : requests) {
                Thread.sleep((int)(Math.random() * 800 + 300));

                System.out.println("\n[Klient " + id + "] Pytam o: " + req);
                out.writeObject(req);
                out.flush();

                try {
                    Object response = in.readObject();

                    List<?> collection = (List<?>) response;

                    System.out.println("[Klient " + id + "] Otrzymano " + collection.size() + " obiektow:");
                    collection.forEach(obj -> System.out.println("  [Klient " + id + "] " + obj));

                } catch (ClassCastException e) {
                    System.err.println("[BLAD] Klient " + id + " - otrzymano zly typ obiektu");
                }
            }
            out.writeObject("KONIEC");
            out.flush();
            System.out.println("\n[KONIEC] Klient " + id + "\n");

        } catch (Exception e) {
            System.err.println("Blad klienta " + id + ": " + e.getMessage());
        }
    }
}