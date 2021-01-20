package it.marco.trinci.db.controller;

import java.util.concurrent.ConcurrentMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.marco.trinci.db.exception.BadRequestException;
import it.marco.trinci.db.exception.NotFoundException;
import it.marco.trinci.db.service.StorageInterface;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/api/v1/db")
@RequiredArgsConstructor
public class DbControllerV1 {
	
	private final StorageInterface storage;

    @PostMapping
    public void post(
    		@RequestBody ConcurrentMap<String, String> map
    	) {
    	
    	notNullOrException(map);
    	map.keySet().stream().forEach(this::notContainsKeyOrException);
    	
    	storage.putAll(map);
    }

    @GetMapping("/{key}")
    public String get(
    		@PathVariable String key
    	) {
        
    	notNullOrException(key);
    	containsKeyOrException(key);
    	
    	return storage.get(key);
    }
    
    @GetMapping("/size")
    public Integer size() {
    	
    	return storage.size();
    }
    
    @GetMapping("/dump")
    public ConcurrentMap<String, String> dump() {
    	
    	return storage.dump();
    }

    @PutMapping("/{key}")
    public void put(
    		@PathVariable String key, 
    		@RequestBody String value
    	) {
    	
    	notNullOrException(key);
    	notNullOrException(value);
    	containsKeyOrException(key);
    	
    	storage.put(key, value);
    }

    @DeleteMapping("/{key}")
    public void delete(
    		@PathVariable String key
    	) {
    	
    	notNullOrException(key);
    	containsKeyOrException(key);
    	
    	storage.remove(key);
    }
    
	private void notNullOrException(Object obj) {
		if(null == obj) {
			throw new BadRequestException();
		}		
	}
    
	private void containsKeyOrException(String key) {
		if(!storage.containsKey(key)) {
			throw new NotFoundException();
		}		
	}
	
	private void notContainsKeyOrException(String key) {
		if(storage.containsKey(key)) {
			throw new BadRequestException();
		}		
	}
}
