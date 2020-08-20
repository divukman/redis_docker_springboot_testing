package com.dimitar.examples.redis.database.redis.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;


@RedisHash(value = "Student", timeToLive=10)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private String id;
    private String name;
}
