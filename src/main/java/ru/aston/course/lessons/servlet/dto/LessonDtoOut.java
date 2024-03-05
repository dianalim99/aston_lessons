package ru.aston.course.lessons.servlet.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class LessonDtoOut {
    private Long id;
    private Long idTeacher;
    private Timestamp date;
    private List<StudentDto> students;

    public LessonDtoOut() {
    }

    public LessonDtoOut(Long id, Long idTeacher, Timestamp date, List<StudentDto> students) {
        this.id = id;
        this.idTeacher = idTeacher;
        this.date = date;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    public List<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDto> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDtoOut lessonDtoOut = (LessonDtoOut) o;
        return Objects.equals(id, lessonDtoOut.id) && Objects.equals(idTeacher, lessonDtoOut.idTeacher) && Objects.equals(date, lessonDtoOut.date) && Objects.equals(students, lessonDtoOut.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTeacher, date, students);
    }
}
