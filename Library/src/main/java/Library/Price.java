package Library;

public class Price extends Book {

    private final double basePrice; // cena nové knihy
    private final Damage damage;    // procento poškození knihy
    private final double borrowPercent; // procento z ceny za půjčení

    public Price(Book book, double basePrice, Damage damage, double borrowPercent) {
        super(book.getId(), book.getTitle(), book.getAutor());
        this.basePrice = basePrice;
        this.damage = damage;
        this.borrowPercent = borrowPercent;
    }

    public double calculatePrice() {
        double price = basePrice * (borrowPercent / 100.0); // cena půjčení

        // odečteme procento poškození
        price *= (100 - damage.getTotalDamage()) / 100.0;

        return price;
    }

    @Override
    public String toString() {
        return super.toString() + " | Půjčovací cena: " + String.format("%.2f", calculatePrice()) + " Kč";
    }
}
