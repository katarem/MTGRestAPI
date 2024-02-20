package aed.spring.mtgrestapi.model;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private List<Card> cards;
}
