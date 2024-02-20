package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
