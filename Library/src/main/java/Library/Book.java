package Library;

public class Book {
    private int id;
    final String title;
    final String author;
    private int yearPublished;
    private String isbn;
    private double basePrice;   // cena knihy
    private int damagePercent;  // procento poškození

    // konstruktor pro novou knihu (bez ID)
    public Book(String title, String author, int yearPublished, String isbn, double basePrice, int damagePercent) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.basePrice = basePrice;
        this.damagePercent = damagePercent;
    }


    // konstruktor s ID (pro načtení z DB)
    public Book(int id, String title, String author, int yearPublished, String isbn, double basePrice, int damagePercent) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.basePrice = basePrice;
        this.damagePercent = damagePercent;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getDamagePercent() {
        return damagePercent;
    }

    public int getId() {
        return id;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDamagePercent(int damagePercent) {
        if (damagePercent < 0) damagePercent = 0;
        if (damagePercent > 100) damagePercent = 100;
        this.damagePercent = damagePercent;
    }
    @Override
    public String toString() {
        return getId() + " | " + getAuthor() + " | " + getTitle() + " | ";
    }


}
