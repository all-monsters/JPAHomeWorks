package cn.com.taiji.domain;

import javax.persistence.*;

/**
 * @program: jpa3
 * @description: book
 * @author: liu yan
 * @create: 2019-07-30 09:56
 */
@Entity

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = true)
    private long bookid;
    @Column(nullable = true)
    private String bookname;

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", bookname='" + bookname + '\'' +
                '}';
    }
}
