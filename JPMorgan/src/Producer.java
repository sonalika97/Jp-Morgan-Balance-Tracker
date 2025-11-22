import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.net.URI;



public class Producer {

	private static final String TARGET_URL = "http://localhost:8080/transaction";
    private static final int RATE_PER_THREAD = 25;
    private static final long MIN_PENCE = 200L * 100L;
    private static final long MAX_PENCE = 500_000L * 100L;

    public static void main(String[] args) throws Exception {

        System.out.println("Producer started. Generating transactions...");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Runnable creditTask = () -> {
            try {
                sendRandomTransaction(true);
            } catch (Exception e) {
                System.err.println("Credit thread exception:");
                e.printStackTrace();
            }
        };

        Runnable debitTask = () -> {
            try {
                sendRandomTransaction(false);
            } catch (Exception e) {
                System.err.println("Debit thread exception:");
                e.printStackTrace();
            }
        };

        long periodMs = 1000 / RATE_PER_THREAD;

        scheduler.scheduleAtFixedRate(creditTask, 0, periodMs, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(debitTask, 0, periodMs, TimeUnit.MILLISECONDS);

        Thread.currentThread().join();
    }

    private static void sendRandomTransaction(boolean isCredit) throws Exception {

        long amount = randomBetween(MIN_PENCE, MAX_PENCE);
        long signedAmount = isCredit ? amount : -amount;

        String id = UUID.randomUUID().toString();
        double pounds = signedAmount / 100.0;

        if (isCredit) {
            System.out.println("[CREDIT] Sending amount: " + pounds + " ID: " + id);
        } else {
            System.out.println("[DEBIT] Sending amount: " + pounds + " ID: " + id);
        }

        String json = "{ \"id\": \"" + id + "\", \"amountPounds\": " + pounds + " }";

        System.out.println("JSON Sent → " + json);

        sendToServer(json);
    }

    private static long randomBetween(long min, long max) {
        return min + (long)(Math.random() * (max - min));
    }

    private static void sendToServer(String jsonPayload) throws Exception {

    	URI uri = URI.create(TARGET_URL);
    	URL url = uri.toURL();
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();


        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonPayload.getBytes());
        }

        int code = conn.getResponseCode();
        if (code != 200) {
            System.err.println("POST failed, HTTP code: " + code);
        } else {
            System.out.println("POST success ✓ Code: " + code);
        }

        conn.disconnect();
    }
}
