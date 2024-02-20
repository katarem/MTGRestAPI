package aed.spring.mtgrestapi.repository;

import aed.spring.mtgrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User getUserByUsernameIsAndPasswordIs(String username, String password);

}
