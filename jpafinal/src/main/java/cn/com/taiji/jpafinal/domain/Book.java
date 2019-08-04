package cn.com.taiji.jpafinal.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: jpafinal
 * @description: book
 * @author: qiao zhan guang
 * @create: 2019-08-01 19:15
 */
    @Entity
    public class Book implements Serializable {

        private static final long serialVersionUID = 5119673746393145493L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;// id

        @Column(name = "book_name")
        private String bookName;

        @Column(name = "book_number")
        private String bookNumber;

        private String remark;

        private Integer flag;

    public Book() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", bookNumber='" + bookNumber + '\'' +
                ", remark='" + remark + '\'' +
                ", flag=" + flag +
                '}';
    }
}
