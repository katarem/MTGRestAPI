package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
    public List<Deck> findByUser_Id(Integer user_id);
    public boolean existsDeckByDeckNameEqualsAndUser_Id(String deckName, int userId);
    public List<Deck> findDecksByColorContainsAndUser_Id(String color, int userId);
    public List<Deck> findDecksByColorContains(String color);

}
