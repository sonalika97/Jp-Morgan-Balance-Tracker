public class Transaction {

    private String id;
    private double amountPounds;

    public Transaction(String id, double amountPounds) {
        this.id = id;
        this.amountPounds = amountPounds;
    }

    public String getId() {
        return id;
    }

    public double getAmountPounds() {
        return amountPounds;
    }

    // Convert to JSON manually (simple string)
    public String toJson() {
        return "{ \"id\": \"" + id + "\", \"amountPounds\": " + amountPounds + " }";
    }

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', amount=" + amountPounds + "}";
    }
}
