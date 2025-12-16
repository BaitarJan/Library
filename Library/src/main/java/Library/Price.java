package Library;

public class Price {

    private final double basePrice;
    private final int damagePercent;
    private final double borrowPercent;

    public Price(Book book, double borrowPercent) {
        this.basePrice = book.getBasePrice();
        this.damagePercent = book.getDamagePercent();
        this.borrowPercent = borrowPercent;
    }

    public double calculatePrice() {
        double price = basePrice * (borrowPercent / 100.0);
        price *= (100 - damagePercent) / 100.0;
        return price;
    }

    // 🔥 NOVÁ VERZE SE ZPOŽDĚNÍM
    public double calculatePrice(long daysRemaining) {
        double price = calculatePrice();

        if (daysRemaining < 0) {
            price *= 1.25; // +25 % pokuta
        }

        return price;
    }

    @Override
    public String toString() {
        return String.format("Půjčovací cena: %.2f Kč", calculatePrice());
    }
}