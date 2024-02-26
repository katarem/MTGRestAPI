package aed.spring.mtgrestapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "deck")
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private int id;
    @Column(name = "deck_name")
    private String deckName;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "deck_color")
    private String color;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "deck_card",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards;
}
