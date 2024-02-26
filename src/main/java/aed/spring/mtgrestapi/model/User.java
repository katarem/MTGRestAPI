package aed.spring.mtgrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "user_username")
    private String username;
    @Column(name = "user_password")
    @JsonIgnore
    private String password;
    @Column(name = "user_image")
    private String profileImg;
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}
