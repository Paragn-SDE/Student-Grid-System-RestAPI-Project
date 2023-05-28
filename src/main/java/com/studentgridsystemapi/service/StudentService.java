package com.studentgridsystemapi.service;

import com.studentgridsystemapi.payload.StudentDTO;
import com.studentgridsystemapi.payload.StudentResponse;
import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    List<StudentDTO> filterStudents(Integer mark, Long id, String name);
    StudentResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);
}
