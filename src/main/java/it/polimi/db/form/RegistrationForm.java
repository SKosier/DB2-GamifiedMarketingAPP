package it.polimi.db.form;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import it.polimi.db.entity.User;

public class RegistrationForm {
	private String username;
	private String password;
	private String email;

	Map<String, List<String>> errors = new HashMap<>();

	public RegistrationForm() {
	}

	/**
	 * Getter za pogreske.
	 * 
	 * @param name Kljuc pogreske
	 * @return Opis pogreske
	 */
	public List<String> getError(String name) {
		return errors.get(name);
	}

	public void setError(String key, String description) {
		if (errors.containsKey(key)) {
			errors.get(key).add(description);
		} else {
			List<String> list = new LinkedList<>();
			list.add(description);
			errors.put(key, list);
		}
	}

	/**
	 * Metoda vraca true ako je formular ispravno popunjen.
	 * 
	 * @return true ako je formular ispravno popunjen.
	 */
	public boolean isValid() {
		return errors.isEmpty();
	}

	/**
	 * Metoda koja vraca true ako opisnik pogresaka sadrzi danu pogresku.
	 * 
	 * @param name Ime pogreske
	 * @return true ako opisnik pogresaka sadrzi danu pogresku.
	 */
	public boolean containsError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Metoda koja priprema podatke prije validacije.
	 * 
	 * @param s Dani string
	 * @return Pripremljeni string
	 */
	private String prepare(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Metoda koja postavlja podatke na temljelju informacija iz http zahtjeva.
	 * 
	 * @param req Http zahtjev s podacima.
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.username = prepare(req.getParameter("username"));
		this.email = prepare(req.getParameter("email"));
		this.password = prepare(req.getParameter("password"));
	}

	/**
	 *
	 * Metoda koja iz danog BlogUsera, puni formular podacima
	 * 
	 * @param r
	 */
	public void popuniIzRecorda(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPasswordHash();
	}

	/**
	 * Metoda koja dani BlogUser puni podacima iz formulara.
	 * 
	 * @param r
	 */
	public void popuniURecord(User r) {
		r.setUsername(this.username);
		r.setEmail(this.email);
		r.setPasswordHash(this.password);
	}

	/**
	 * Metoda koja vrsi validaciju unesenih podataka
	 */
	public void validate() {
		errors.clear();

		if (this.username.isEmpty()) {
			setError("username", "Username is necessary!");
		} else if (this.username.length() > 30) {
			setError("username", "Username is too big!");
		}

		if (this.email.isEmpty()) {
			setError("email", "E-mail is necessary!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				setError("email", "Wrong email format!");
			}
		}

		if (this.password.isEmpty()) {
			setError("password", "Password is necessary!");
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Map<String, List<String>> getErrors() {
		return errors;
	}

}
