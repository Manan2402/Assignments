package exception;

public class LibExcep {
    public static class BookNotAvailableException extends RuntimeException {
        public BookNotAvailableException(String msg) { super(msg); }
    }
    public static class MemberNotFoundException extends RuntimeException {
        public MemberNotFoundException(String msg) { super(msg); }
    }
    public static class OverdueBookException extends RuntimeException {
        public OverdueBookException(String msg) { super(msg); }
    }
    public static class BookNotFoundException extends RuntimeException {
        public BookNotFoundException(String msg) { super(msg); }
    }
}