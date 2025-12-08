package Library;

public class Book {
    final int id;
    final String title;
    final String author;

    //kostruktor knihy
    public Book(int id, String title, String autor) {
        this.id = id;
        this.title = title;
        this.author = autor;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return getId() + " | " + getAuthor() + " | " + getTitle() + " | ";
    }


}
