package it.polimi.db.entity;

import java.util.HashSet;
import java.util.List;

public final class CWSingleton {
	
	public static volatile HashSet<String> bannedWords = null;
	
	private CWSingleton() {}
	
	public static void setBannedWords(List<String> bw) {
		bannedWords = new HashSet<>(bw);
	}
}
