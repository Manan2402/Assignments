import MainLib.*;
import Services.*;
import exception.LibExcep.*;
import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Library s = new Library();
        Scanner sc = new Scanner(System.in);
        Dues monitor = new Dues(s);
        monitor.start();
        while (true) {
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. List Available Books");
            System.out.println("8. List Members with Borrowed Books");
            System.out.println("9. List Overdue Records");
            System.out.println("10. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Book ID: ");
                    String bookId = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    boolean addBookSuccess = s.addBook(new Book(bookId, title, author));
                    if (addBookSuccess) System.out.println("Book added successfully.");
                    else System.out.println("Book ID already exists. Book not added.");
                    break;
                case 2:
                    System.out.print("Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    boolean addMemberSuccess = s.addMember(new Member(memberId, name, email));
                    if (addMemberSuccess) System.out.println("Member added successfully.");
                    else System.out.println("Member ID already exists. Member not added.");
                    break;
                case 3:
                    System.out.print("Book ID: ");
                    String issueBookId = sc.nextLine();
                    System.out.print("Member ID: ");
                    String issueMemberId = sc.nextLine();
                    try {
                        boolean issued = s.issueBook(issueBookId, issueMemberId, LocalDate.now(), LocalDate.now().plusDays(7));
                        if (issued) System.out.println("Book issued successfully.");
                        else System.out.println("Book could not be issued.");
                    } catch (BookNotFoundException | BookNotAvailableException | MemberNotFoundException | OverdueBookException e) {
                        System.out.println("Issue failed: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Book ID: ");
                    String returnBookId = sc.nextLine();
                    System.out.print("Member ID: ");
                    String returnMemberId = sc.nextLine();
                    try {
                        boolean returned = s.returnBook(returnBookId, returnMemberId);
                        if (returned) System.out.println("Book returned successfully.");
                        else System.out.println("No matching lending record found. Book not returned.");
                    } catch (BookNotFoundException e) {
                        System.out.println("Return failed: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Title: ");
                    String searchTitle = sc.nextLine();
                    List<Book> foundByTitle = s.searchBooksByTitle(searchTitle);
                    if (foundByTitle.isEmpty()) System.out.println("No books found with this title.");
                    else for (Book b : foundByTitle) System.out.println(b.bookId + " " + b.title + " " + b.author);
                    break;
                case 6:
                    System.out.print("Author: ");
                    String searchAuthor = sc.nextLine();
                    List<Book> foundByAuthor = s.searchBooksByAuthor(searchAuthor);
                    if (foundByAuthor.isEmpty()) System.out.println("No books found with this author.");
                    else for (Book b : foundByAuthor) System.out.println(b.bookId + " " + b.title + " " + b.author);
                    break;
                case 7:
                    List<Book> available = s.getAvailableBooks();
                    if (available.isEmpty()) System.out.println("No books available.");
                    else for (Book b : available) System.out.println(b.bookId + " " + b.title + " " + b.author);
                    break;
                case 8:
                    List<Member> borrowed = s.getMembersWithBorrowedBooks();
                    if (borrowed.isEmpty()) System.out.println("No members with borrowed books.");
                    else for (Member m : borrowed) System.out.println(m.memberId + " " + m.name + " " + m.email);
                    break;
                case 9:
                    List<LendingRecords> overdue = s.getOverdueRecords();
                    if (overdue.isEmpty()) System.out.println("No overdue records.");
                    else for (LendingRecords r : overdue) System.out.println(r.book.title + " " + r.member.name + " Due: " + r.dueDate);
                    break;
                case 10:
                    monitor.interrupt();
                    sc.close();
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}