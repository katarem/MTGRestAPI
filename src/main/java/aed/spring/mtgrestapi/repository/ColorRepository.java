package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}
