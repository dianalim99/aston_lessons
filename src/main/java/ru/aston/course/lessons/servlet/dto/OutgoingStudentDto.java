package ru.aston.course.lessons.servlet.dto;

import ru.aston.course.lessons.model.StudentGrade;

public class OutgoingStudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private StudentGrade grade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudentGrade getGrade() {
        return grade;
    }

    public void setGrade(StudentGrade grade) {
        this.grade = grade;
    }
}
