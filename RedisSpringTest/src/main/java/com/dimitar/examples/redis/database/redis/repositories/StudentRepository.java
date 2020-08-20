package com.dimitar.examples.redis.database.redis.repositories;

import com.dimitar.examples.redis.database.redis.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository  extends CrudRepository<Student, String> {
}
