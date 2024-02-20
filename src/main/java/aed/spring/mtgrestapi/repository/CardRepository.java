package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
