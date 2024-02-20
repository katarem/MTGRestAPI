package aed.spring.mtgrestapi.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_username")
    private String username;
    @Column(name = "user_password")
    private String password;
}
