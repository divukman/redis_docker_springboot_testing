package com.dimitar.examples.redis.web.rest;

import com.dimitar.examples.redis.database.redis.RedisLock;
import com.dimitar.examples.redis.database.redis.entities.Student;
import com.dimitar.examples.redis.database.redis.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    public static final String LOCK_KEY = "lock_key_example";
    public static final String LOCK_VALUE = "lock_value_exaple";

    private final StudentRepository studentRepository;
    private final RedisLock lock;


    @GetMapping("/write")
    public ResponseEntity<?> testWrite() {
        final Student savedStudent = studentRepository.save(new Student(UUID.randomUUID().toString(), "Fox Mulder"));

        final boolean lock1Acquired = lock.lock(LOCK_KEY, LOCK_VALUE);
        System.out.println("Lock 1 acquired: " + lock1Acquired);

        final long count = studentRepository.count();
        System.out.println("Entities in repo: " + count);
        if (count > 0) {
            studentRepository.findAll().forEach(student -> System.out.println(student));
        }

        //final boolean lock1Released = lock.unlock(LOCK_KEY, LOCK_VALUE);
        //System.out.println("Lock 1 released: " + lock1Released);

        return new ResponseEntity<String>("OK!", HttpStatus.CREATED);
    }

    @GetMapping("/write2")
    public ResponseEntity<?> testWrite2() {
        final Student savedStudent = studentRepository.save(new Student(UUID.randomUUID().toString(), "Fox Mulder"));

        final boolean lock1Acquired = lock.lock(LOCK_KEY, LOCK_VALUE);
        System.out.println("Lock 1 acquired: " + lock1Acquired);
        if (lock1Acquired) {
            final long count = studentRepository.count();
            System.out.println("Entities in repo: " + count);
            if (count > 0) {
                studentRepository.findAll().forEach(student -> System.out.println(student));
            }
        } else {
            System.out.println("Cant acquire the lock!");
        }

        //final boolean lock1Released = lock.unlock(LOCK_KEY, LOCK_VALUE);
        //System.out.println("Lock 1 released: " + lock1Released);

        return new ResponseEntity<String>("OK!", HttpStatus.CREATED);
    }
}
