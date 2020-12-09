package it.polimi.db.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import it.polimi.db.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String name);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameAndPasswordHash(String username, String passwordHash);

	int countByUsername(String username);

	int countByEmail(String email);

}
