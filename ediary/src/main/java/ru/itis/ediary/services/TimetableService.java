package ru.itis.ediary.services;

import ru.itis.ediary.dto.timetable.NewTimetableDto;
import ru.itis.ediary.dto.timetable.TimetableDto;
import ru.itis.ediary.dto.timetable.TimetablePage;

import java.util.UUID;

public interface TimetableService {
    TimetablePage getAllTimetables();
    TimetableDto createTimetable(NewTimetableDto timetable);
    TimetableDto findById(UUID id);
    void deleteById(UUID id);
}
