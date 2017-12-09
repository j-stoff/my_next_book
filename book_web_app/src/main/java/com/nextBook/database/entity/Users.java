package com.nextBook.database.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Users extends BaseEntity implements Serializable {

  @Id
  @NotNull
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name="increment", strategy = "increment")
  @Column(name = "user_id", unique = true, nullable = false)
  private int user_id;

  @NotNull
  @Column(name = "user_name")
  private String user_name;


  @NotNull
  @Column(name = "user_password")
  private String user_password;

  @Column(name = "email")
  private String email;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
  private List<User_roles> roles;

  /*
  @OneToMany(mappedBy = "bookPerUser", fetch = FetchType.EAGER)
  @Cascade({CascadeType.SAVE_UPDATE ,CascadeType.DELETE})
  private Set<UserBooksEntity> booksForUser;
  */

  @ManyToMany(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
  @JoinTable(name = "user_books",
          joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false)},
          inverseJoinColumns = {@JoinColumn(name = "id_books", nullable = false, updatable = false)})
  private Set<Book> booksForUser;

  public Users() {
    roles = new ArrayList<>();
    booksForUser = new HashSet<Book>(0);
  }

  public Users(int id, String username, String password, String email) {
    this();
    this.user_id = id;
    this.user_name = username;
    this.user_password = password;
    this.email = email;
  }

  public int getId() {
    return user_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getUser_name() {
    return user_name;
  }


  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }


  public String getUser_password() {
    return user_password;
  }

  public void setUser_password(String user_password) {
    this.user_password = user_password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<User_roles> getRoles() {
    return this.roles;
  }

  public void setRoles(List<User_roles> newRoles) {
    this.roles = newRoles;
  }

  public Set<Book> getBooksForUser() {
    return booksForUser;
  }

  public void setBooksForUser(Set<Book> books) {
    this.booksForUser = books;
  }

  public String toLog() {
    return "Id: " + user_id + ", Username: " + user_name + ", Password: " + user_password + ", Email: " + email;
  }

  @Override
  public String toString() {
    String newLine = System.getProperty("line.separator");
    String user = "User id: " + user_id + newLine;
    user += "Username: " + user_name + newLine;
    user += "Password: " + user_password + newLine;
    user += "Email: " + email + newLine;

    return user;
  }
}
