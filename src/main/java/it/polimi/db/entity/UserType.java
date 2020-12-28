package it.polimi.db.entity;

public enum UserType {
	ADMINISTRATOR, PARTICIPANT, BANNED;

	public static final UserType[] regTYPES = { PARTICIPANT, ADMINISTRATOR, BANNED };

	public static UserType forName(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null for usertype");
		}

		if (name.toUpperCase().equals("PARTICIPANT")) {
			return PARTICIPANT;

		} else if (name.toUpperCase().equals("ADMINISTRATOR")) {
			return ADMINISTRATOR;

		} else if (name.toUpperCase().equals("BANNED")) {
			return BANNED;

		}
		throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any UserType");
	}
}
