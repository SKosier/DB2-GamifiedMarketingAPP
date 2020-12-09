package it.polimi.db.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.polimi.db.dao.UserRepository;
import it.polimi.db.entity.User;
import it.polimi.db.service.UserService;
import it.polimi.db.service.exception.EntityMissingException;
import it.polimi.db.service.exception.RequestDeniedException;

@Service
public class UserServiceJPA implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> listAll() {
		return userRepo.findAll();
	}

	@Override
	public User fetch(int userId) {
		return findById(userId).orElseThrow(() -> new EntityMissingException(User.class, userId));
	}

	@Override
	public Optional<User> findById(int userId) {
		return userRepo.findById(userId);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		Assert.notNull(username, "Username must be given");
		return userRepo.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Assert.notNull(email, "Email must be given");
		return userRepo.findByEmail(email);
	}

	@Override
	public Optional<User> findByUsernameAndPasswordHash(String username, String passwordHash) {
		Assert.notNull(username, "Username must be given");
		Assert.notNull(passwordHash, "PasswordHash must be given");
		return userRepo.findByUsernameAndPasswordHash(username, passwordHash);
	}

	@Override
	public int countByUsername(String username) {
		return userRepo.countByUsername(username);
	}

	@Override
	public int countByEmail(String email) {
		return userRepo.countByEmail(email);
	}

	@Override
	public User createUser(User user) {
		checkIfValid(user);
		Assert.isNull(user.getId(), "User ID must be null, not: " + user.getId());
		if (userRepo.countByUsername(user.getUsername()) > 0)
			throw new RequestDeniedException("User with username " + user.getUsername() + " already exsists!");
		if (userRepo.countByEmail(user.getEmail()) > 0)
			throw new RequestDeniedException("User with email " + user.getEmail() + " already exsists!");
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user) {
		checkIfValid(user);
		Integer userId = user.getId();
		if (!userRepo.existsById(userId))
			throw new EntityMissingException("User doesn't exists!");
		return userRepo.saveAndFlush(user);
	}

	private void checkIfValid(User user) {
		Assert.notNull(user, "User must be given!");
		// email and username check
		Assert.hasText(user.getUsername(), "Username must be given!");
		Assert.hasText(user.getEmail(), "Email must be given!");
	}
}
