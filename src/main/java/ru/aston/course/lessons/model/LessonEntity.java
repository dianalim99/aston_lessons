package ru.aston.course.lessons.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LessonEntity {
    private Long id;
    private Long idTeacher;
    private Timestamp date;
    private List<StudentEntity> students = new ArrayList<>();

    public LessonEntity() {
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

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonEntity that = (LessonEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(idTeacher, that.idTeacher) && Objects.equals(date, that.date) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTeacher, date, students);
    }

    @Override
    public String toString() {
        return "LessonEntity{" +
                "id=" + id +
                ", idTeacher=" + idTeacher +
                ", date=" + date +
                ", students=" + students +
                '}';
    }
}
