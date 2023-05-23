package ru.zenchenko.courseproject.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class Person {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    String login;
    @Column
    String password;
    @Column
    int level;
    @Column
    String role;

    public Person() {
    }

    public Person(String login, String password, int level, String role) {
        this.login = login;
        this.password = password;
        this.level = level;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                ", role='" + role + '\'' +
                '}';
    }
}
