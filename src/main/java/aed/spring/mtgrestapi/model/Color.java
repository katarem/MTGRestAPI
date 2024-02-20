package aed.spring.mtgrestapi.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private int id;
    @Column(name = "color_name")
    private String colorName;
    @OneToMany(mappedBy = "color")
    private List<Deck> decks;
    @OneToMany(mappedBy = "color")
    private List<Card> cards;
}
