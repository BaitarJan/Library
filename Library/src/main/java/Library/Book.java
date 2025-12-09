package Library;

public class Book {
    final int id;
    final String title;
    final String autor;

    //kostruktor knihy
    public Book(int id, String title, String autor) {
        this.id = id;
        this.title = title;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return getId() + " | " + getAutor() + " | " + getTitle() + " | ";
    }


}
