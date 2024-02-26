package aed.spring.mtgrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "card")
public class Card {
    @Id
    @Column(name = "card_id")
    private String UUID;
    @Column(name = "card_name")
    private String cardName;
    @Column(name = "card_img")
    private String img;
    @ManyToMany(mappedBy = "cards",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Deck> decks;
    @Column(name = "card_color")
    private String color;
}
