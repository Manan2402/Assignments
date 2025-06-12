package MainLib;

public class Book implements Comparable<Book> {
    public String bookId, title, author;
    public boolean isIssued;
    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }
}