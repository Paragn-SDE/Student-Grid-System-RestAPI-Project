package com.studentgridsystemapi.controller;
import com.studentgridsystemapi.payload.StudentDTO;
import com.studentgridsystemapi.payload.StudentResponse;
import com.studentgridsystemapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public StudentResponse getAllStudents(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return studentService.getAllStudents(pageNo, pageSize, sortBy, sortDir);
    }


    @GetMapping("/filter")
    public ResponseEntity<List<StudentDTO>> filterStudents(@RequestParam(name = "mark", required = false) Integer mark,
                                                           @RequestParam(name = "id", required = false) Long id,
                                                           @RequestParam(name = "name", required = false) String name) {
        List<StudentDTO> filteredStudents = studentService.filterStudents(mark, id, name);
        return ResponseEntity.ok(filteredStudents);
    }


    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }


}
