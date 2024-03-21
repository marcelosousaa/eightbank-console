package br.com.cdb.java.grupo4.eightbank.model.user;

import br.com.cdb.java.grupo4.eightbank.enuns.UserRole;

public abstract class User {
    private long id;
    private String email;
    private String password;
    private String name;
    private UserRole userRole;

    public User(String email, String password, String name, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole(){
        return this.userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
