package com.dimitar.examples.redis.web.rest;

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

    private final StudentRepository studentRepository;


    @GetMapping("/write")
    public ResponseEntity<?> testWrite() {
        Student savedStudent = studentRepository.save(new Student(UUID.randomUUID().toString(), "Fox Mulder"));
        
        final long count = studentRepository.count();
        System.out.println("Entities in repo: " + count);
        if (count > 0) {
            studentRepository.findAll().forEach(student -> System.out.println(student));
        }


        // @todo:
        // Refresh entry? -> Make a new entry under same KEY
        // Expired listener?

        return new ResponseEntity<String>("Student has been saved!", HttpStatus.CREATED);

    }
}
