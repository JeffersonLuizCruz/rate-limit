package com.dnia.eaas.ratelimit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.dnia.eaas.model.enums.Role;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

@Service
public class ProfileLimit {
	
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey, this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        Role pricingPlan = Role.resolvePlanFromApiKey(apiKey);
        return Bucket4j.builder()
            .addLimit(pricingPlan.getLimit())
            .build();
    }

}
