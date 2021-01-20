package it.marco.trinci.db.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import it.marco.trinci.db.service.StorageInterface;

public class MapStorage implements StorageInterface{

	ConcurrentMap<String, String> storage = new ConcurrentHashMap<>();
	
	@Override
	public void remove(String key) {
		storage.remove(key);
	}

	@Override
	public void putAll(ConcurrentMap<String, String> map) {
		storage.putAll(map);
	}

	@Override
	public String get(String key) {
		return storage.get(key);
	}

	@Override
	public String put(String key, String value) {
		return storage.put(key, value);
	}

	@Override
	public boolean containsKey(String key) {
		return storage.containsKey(key);
	}

	@Override
	public Integer size() {
		return storage.size();
	}

	@Override
	public ConcurrentMap<String, String> dump() {
		return storage;
	}
}