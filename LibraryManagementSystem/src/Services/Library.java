package Services;

import MainLib.*;
import exception.LibExcep.*;
import java.util.*;
import java.time.*;
import java.util.stream.*;

public class Library {
    public Map<String, Book> books = new HashMap<>();
    public Map<String, Member> members = new HashMap<>();
    public List<LendingRecords> records = new ArrayList<>();

    public boolean addBook(Book b) {
        if (books.containsKey(b.bookId)) return false;
        books.put(b.bookId, b);
        return true;
    }
    public boolean removeBook(String id) {
        return books.remove(id) != null;
    }
    public boolean addMember(Member m) {
        if (members.containsKey(m.memberId)) return false;
        members.put(m.memberId, m);
        return true;
    }
    public boolean removeMember(String id) {
        return members.remove(id) != null;
    }

    public Book findBookById(String id) {
        Book book = books.get(id);
        if (book == null) throw new BookNotFoundException("Book not found: " + id);
        return book;
    }

    public Member findMemberById(String id) {
        Member m = members.get(id);
        if (m == null) throw new MemberNotFoundException("Member not found: " + id);
        return m;
    }

    public List<Book> searchBooksByTitle(String title) {
        return books.values().stream().filter(b -> b.title.contains(title)).collect(Collectors.toList());
    }
    public List<Book> searchBooksByAuthor(String author) {
        return books.values().stream().filter(b -> b.author.contains(author)).collect(Collectors.toList());
    }
    public List<Member> searchMembersByName(String name) {
        return members.values().stream().filter(m -> m.name.contains(name)).collect(Collectors.toList());
    }
    public List<Member> searchMembersByEmail(String email) {
        return members.values().stream().filter(m -> m.email.equals(email)).collect(Collectors.toList());
    }

    public boolean issueBook(String bookId, String memberId, LocalDate issueDate, LocalDate dueDate) {
        Book b = books.get(bookId);
        if (b == null) throw new BookNotFoundException("Book not found: " + bookId);
        if (b.isIssued) throw new BookNotAvailableException("Book not available");
        Member m = members.get(memberId);
        if (m == null) throw new MemberNotFoundException("Member not found: " + memberId);
        boolean hasOverdue = records.stream().anyMatch(r -> r.member.memberId.equals(memberId) && r.returnDate == null && r.dueDate.isBefore(LocalDate.now()));
        if (hasOverdue) throw new OverdueBookException("Member has overdue books");
        b.isIssued = true;
        records.add(new LendingRecords(UUID.randomUUID().toString(), b, m, issueDate, dueDate));
        return true;
    }

    public boolean returnBook(String bookId, String memberId) {
        Book b = books.get(bookId);
        if (b == null) throw new BookNotFoundException("Book not found: " + bookId);
        for (LendingRecords r : records) {
            if (r.book.bookId.equals(bookId) && r.member.memberId.equals(memberId) && r.returnDate == null) {
                r.returnDate = LocalDate.now();
                r.book.isIssued = false;
                return true;
            }
        }
        return false;
    }

    public List<Book> getAvailableBooks() {
        return books.values().stream().filter(b -> !b.isIssued).collect(Collectors.toList());
    }
    public List<Member> getMembersWithBorrowedBooks() {
        return records.stream().filter(r -> r.returnDate == null).map(r -> r.member).distinct().collect(Collectors.toList());
    }
    public List<LendingRecords> getOverdueRecords() {
        return records.stream().filter(r -> r.returnDate == null && r.dueDate.isBefore(LocalDate.now())).collect(Collectors.toList());
    }
    public List<Book> getSortedBooksByTitle() {
        List<Book> list = new ArrayList<>(books.values());
        Collections.sort(list);
        return list;
    }
}