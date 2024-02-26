package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    public Card findByCardNameEqualsIgnoreCase(String cardName);

}
