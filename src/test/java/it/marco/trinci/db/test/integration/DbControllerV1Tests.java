package it.marco.trinci.db.test.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.marco.trinci.db.controller.DbControllerV1;
import it.marco.trinci.db.exception.BadRequestException;
import it.marco.trinci.db.exception.NotFoundException;
import it.marco.trinci.db.service.StorageInterface;
import it.marco.trinci.db.service.impl.MapStorage;

class DbControllerV1Tests {

	DbControllerV1 dbControllerV1;
	
	@BeforeEach
	void resetController() {
		StorageInterface storage = new MapStorage();
		dbControllerV1 = new DbControllerV1(storage);
	}
	
	@Test
	void postGetTest() {
		ConcurrentMap<String, String> map1 = new ConcurrentHashMap<>();
		map1.put("key1", "1");
		map1.put("key2", "2");
		map1.put("key3", "3");
		ConcurrentMap<String, String> map2 = new ConcurrentHashMap<>();
		map2.put("key4", "4");
		map2.put("key5", "4");
		
		dbControllerV1.post(map1);
		dbControllerV1.post(map2);
		
		assertEquals(map1.size() + map2.size(), dbControllerV1.size());
		map1.forEach((k, v) -> assertEquals(v, dbControllerV1.get(k)));
		map2.forEach((k, v) -> assertEquals(v, dbControllerV1.get(k)));
	}
	
	@Test
	void dumpTest() {
		ConcurrentMap<String, String> expected = new ConcurrentHashMap<>();
		expected.put("key1", "1");
		expected.put("key2", "2");
		expected.put("key3", "3");
		
		dbControllerV1.post(expected);
		ConcurrentMap<String, String> dump = dbControllerV1.dump();
		
		assertEquals(expected, dump);
	}
	
	@Test
	void duplicateKeyTest() {
		String key = "key1";
		ConcurrentMap<String, String> map1 = singleKeyMap(key, "1");
		ConcurrentMap<String, String> map2 = singleKeyMap(key, "2");
		
		dbControllerV1.post(map1);
		
		Assertions.assertThrows(BadRequestException.class, () -> {
			dbControllerV1.post(map2);
		});
		assertEquals(map1.size(), dbControllerV1.size());
		map1.forEach((k, v) -> assertEquals(v, dbControllerV1.get(k)));
	}
	
	@Test
	void putTest() {
		String key = "key2";
		String value = "2";
		String newValue = "5";

		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
		map.put("key1", "1");
		map.put(key, value);
		map.put("key3", "3");
				
		dbControllerV1.post(map);
		assertEquals(value, dbControllerV1.get(key));
		
		dbControllerV1.put(key, newValue);
		
		assertEquals(map.size(), dbControllerV1.size());
		assertEquals(newValue, dbControllerV1.get(key));
		map.keySet().stream()
			.filter(s -> !key.equals(s))
			.forEach(k -> assertEquals(map.get(k), dbControllerV1.get(k)));
	}
	
	@Test
	void deleteTest() {
		String key = "key2";

		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
		map.put("key1", "1");
		map.put(key, 	"2");
		map.put("key3", "3");
		
		dbControllerV1.post(map);
		dbControllerV1.delete(key);
		
		assertEquals(map.size() -1, dbControllerV1.size());
		map.keySet().stream()
			.filter(s -> !key.equals(s))
			.forEach(k -> assertEquals(map.get(k), dbControllerV1.get(k)));
	}
	
	@Test
	void postEmptyTest() {
		dbControllerV1.post(new ConcurrentHashMap<>());
		assertEquals(0, dbControllerV1.size());
	}

	@Test
	void notFoundTest() {
		//get
		Assertions.assertThrows(NotFoundException.class, () -> {
			dbControllerV1.get("key1");
		});
		//put
		Assertions.assertThrows(NotFoundException.class, () -> {
			dbControllerV1.put("key1", "");
		});
		//delete
		Assertions.assertThrows(NotFoundException.class, () -> {
			dbControllerV1.delete("key1");
		});
		assertEquals(0, dbControllerV1.size());
	}
	
	@Test
	void nullParameterTest() {
		//post
		Assertions.assertThrows(BadRequestException.class, () -> {
			dbControllerV1.post(null);
		});
		//get
		Assertions.assertThrows(BadRequestException.class, () -> {
			dbControllerV1.get(null);
		});
		//delete
		Assertions.assertThrows(BadRequestException.class, () -> {
			dbControllerV1.delete(null);
		});
		//put
		Assertions.assertThrows(BadRequestException.class, () -> {
			dbControllerV1.put(null, "");
		});
		Assertions.assertThrows(BadRequestException.class, () -> {
			dbControllerV1.put("", null);
		});
		assertEquals(0, dbControllerV1.size());
	}
	
	private ConcurrentMap<String, String> singleKeyMap(String key, String value) {
		ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

		map.put(key, value);
		
		return map;
	}
}
