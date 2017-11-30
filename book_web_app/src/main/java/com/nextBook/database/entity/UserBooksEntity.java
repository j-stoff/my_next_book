package com.nextBook.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user_books", schema = "ml_training")
public class UserBooksEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_books", referencedColumnName = "id_books", nullable = false)
    private Book bookPerUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Users userForBooks;
    /*
    private int userId;
    private int idBooks;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "id_books")
    public int getIdBooks() {
        return idBooks;
    }

    public void setIdBooks(int idBooks) {
        this.idBooks = idBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBooksEntity that = (UserBooksEntity) o;

        if (userId != that.userId) return false;
        if (idBooks != that.idBooks) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + idBooks;
        return result;
    }
    */

    public Book getBook() {
        return bookPerUser;
    }

    public void setBook(Book book) {
        this.bookPerUser = book;
    }

    public Users getUser() {
        return userForBooks;
    }

    public void setUser(Users user) {
        this.userForBooks = user;
    }
}
