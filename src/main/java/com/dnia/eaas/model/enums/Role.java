package com.dnia.eaas.model.enums;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

public enum Role {
	
	ADMINISTRATOR(5),
	TRIAL(3);
	
	private int bucketCapacity;
	
	Role(int bucketCapacity){
		this.bucketCapacity = bucketCapacity;
	}
	
    public Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofMinutes(1)));
    }
    
    public int bucketCapacity() {
        return bucketCapacity;
    }
    
    public static Role resolvePlanFromApiKey(String owner) {
        if (owner.equalsIgnoreCase(TRIAL.name())) {
            return TRIAL;
        
        } else if (owner.equalsIgnoreCase(ADMINISTRATOR.name())) {
            return ADMINISTRATOR;
        }
        return TRIAL;
    }
}
