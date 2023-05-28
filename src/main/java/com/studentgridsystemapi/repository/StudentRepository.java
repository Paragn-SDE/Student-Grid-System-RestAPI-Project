package com.studentgridsystemapi.repository;
import com.studentgridsystemapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByTotalMarks(Integer mark);
    Optional<Student> findById(Long id);
}
