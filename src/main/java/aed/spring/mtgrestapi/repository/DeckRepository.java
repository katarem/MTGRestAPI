package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
}
