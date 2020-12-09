package it.polimi.db.service;

import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.User;

public interface UserService {
	List<User> listAll();

	User fetch(int userId);

	User createUser(User user);

	User updateUser(User user);

	Optional<User> findById(int userId);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameAndPasswordHash(String username, String passwordHash);

	int countByUsername(String username);

	int countByEmail(String email);
}
