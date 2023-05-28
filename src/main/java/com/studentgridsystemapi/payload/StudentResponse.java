package com.studentgridsystemapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private List<StudentDTO> totalMarks;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPage;
    private boolean last;

    public static StudentResponse build(List<StudentDTO> totalMarks, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        return new StudentResponse(totalMarks, pageNo, pageSize, totalElements, totalPages, last);
    }
}
