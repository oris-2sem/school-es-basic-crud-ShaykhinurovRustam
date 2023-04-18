package ru.itis.ediary.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.ediary.dto.student.NewStudentDto;
import ru.itis.ediary.dto.student.StudentDto;
import ru.itis.ediary.dto.student.StudentPage;
import ru.itis.ediary.models.Group;
import ru.itis.ediary.models.Parent;
import ru.itis.ediary.models.Student;
import ru.itis.ediary.repositories.GroupRepository;
import ru.itis.ediary.repositories.ParentRepository;
import ru.itis.ediary.repositories.StudentRepository;
import ru.itis.ediary.services.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.itis.ediary.dto.student.StudentDto.from;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final GroupRepository groupRepository;

    @Override
    public StudentPage getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return StudentPage.builder()
                .studentList(from(students))
                .build();
    }

    @Override
    public StudentDto createStudent(NewStudentDto newStudent) {
        Optional<Parent> parent = parentRepository.findById(newStudent.getParentId());
        Optional<Group> group = groupRepository.findById(newStudent.getGroupId());

        Student student = Student.builder()
                .taskList(newStudent.getTaskList())
                .firstName(newStudent.getFirstName())
                .group(group.get())
                .rating(newStudent.getRating())
                .parent(parent.get())
                .build();

        studentRepository.save(student);
        return from(student);
    }

    @Override
    public StudentDto findById(UUID id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(StudentDto::from).orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        studentRepository.deleteById(id);
    }
}
