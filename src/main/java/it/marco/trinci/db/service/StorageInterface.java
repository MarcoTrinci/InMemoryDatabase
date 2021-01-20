package it.marco.trinci.db.service;

import java.util.concurrent.ConcurrentMap;

public interface StorageInterface {

	void remove(String key);

	void putAll(ConcurrentMap<String, String> map);

	String get(String key);

	String put(String key, String value);

	boolean containsKey(String key);

	Integer size();

	ConcurrentMap<String, String> dump();

}
