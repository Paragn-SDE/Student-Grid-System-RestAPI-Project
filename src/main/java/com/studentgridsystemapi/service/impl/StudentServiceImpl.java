package com.studentgridsystemapi.service.impl;

import com.studentgridsystemapi.entity.Student;
import com.studentgridsystemapi.payload.StudentDTO;
import com.studentgridsystemapi.payload.StudentResponse;
import com.studentgridsystemapi.repository.StudentRepository;
import com.studentgridsystemapi.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


        private final StudentRepository studentRepository;
        private final ModelMapper modelMapper;

        @Autowired
        public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
            this.studentRepository = studentRepository;
            this.modelMapper = modelMapper;
        }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student createdStudent = studentRepository.save(student);
        return convertToDTO(createdStudent);
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }

    @Override
        public StudentResponse  getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Student> posts = studentRepository.findAll(pageable);

        // get content for page object
        List<Student> listOfStudents = posts.getContent();

        List<StudentDTO> totalMarks= listOfStudents.stream().map((element) -> modelMapper.map(element, StudentDTO.class)).collect(Collectors.toList());

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setTotalMarks(totalMarks);
        studentResponse.setPageNo(posts.getNumber());
        studentResponse.setPageSize(posts.getSize());
        studentResponse.setTotalElements(posts.getTotalElements());
        studentResponse.setTotalPage(posts.getTotalPages());
        studentResponse.setLast(posts.isLast());

        return studentResponse;
    }


    @Override
    public List<StudentDTO> filterStudents(Integer mark, Long id, String name) {
        List<Student> filteredStudents;

        if (mark != null) {
            filteredStudents = studentRepository.findByTotalMarks(mark);
        } else if (id != null) {
            Optional<Student> student = studentRepository.findById(id);
            filteredStudents = student.map(Collections::singletonList).orElseGet(Collections::emptyList);
        } else if (name != null) {
            filteredStudents = studentRepository.findByNameContainingIgnoreCase(name);
        } else {
            filteredStudents = studentRepository.findAll();
        }

        return filteredStudents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private StudentDTO convertToDTO(Student student) {
            return modelMapper.map(student, StudentDTO.class);
        }
}


