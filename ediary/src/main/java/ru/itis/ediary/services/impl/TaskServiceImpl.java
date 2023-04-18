package ru.itis.ediary.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.ediary.dto.task.NewTaskDto;
import ru.itis.ediary.dto.task.TaskDto;
import ru.itis.ediary.dto.task.TaskPage;
import ru.itis.ediary.models.Lesson;
import ru.itis.ediary.models.Student;
import ru.itis.ediary.models.Task;
import ru.itis.ediary.repositories.LessonRepository;
import ru.itis.ediary.repositories.StudentRepository;
import ru.itis.ediary.repositories.TaskRepository;
import ru.itis.ediary.services.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.itis.ediary.dto.task.TaskDto.from;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    @Override
    public TaskPage getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return TaskPage.builder()
                .taskList(from(tasks))
                .build();
    }

    @Override
    public TaskDto createTask(NewTaskDto newTask) {
        Optional<Student> student = studentRepository.findById(newTask.getStudentId());
        Optional<Lesson> lesson = lessonRepository.findById(newTask.getLessonId());

        Task task = Task.builder()
                .commentary(newTask.getCommentary())
                .description(newTask.getDescription())
                .lesson(lesson.get())
                .mark(newTask.getMark())
                .student(student.get())
                .build();

        taskRepository.save(task);
        return from(task);
    }

    @Override
    public TaskDto findById(UUID id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(TaskDto::from).orElse(null);
    }

    @Override
    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }
}
