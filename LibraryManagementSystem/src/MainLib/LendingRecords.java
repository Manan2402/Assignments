package MainLib;

import java.time.LocalDate;

public class LendingRecords {
    public String recordId;
    public Book book;
    public Member member;
    public LocalDate issueDate, dueDate, returnDate;
    public LendingRecords(String recordId, Book book, Member member, LocalDate issueDate, LocalDate dueDate) {
        this.recordId = recordId;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }
}