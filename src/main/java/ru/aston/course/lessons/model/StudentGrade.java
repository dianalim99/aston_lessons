package ru.aston.course.lessons.model;

public enum StudentGrade {
    GRADE_1A("1A"),
    GRADE_1B("1B"),
    GRADE_2A("2A"),
    GRADE_2B("2B");

    StudentGrade(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }

    public static StudentGrade fromName(String name) {
        return switch (name) {
            case "1A" -> GRADE_1A;
            case "1B" -> GRADE_1B;
            case "2A" -> GRADE_2A;
            case "2B" -> GRADE_2B;
            default -> throw new IllegalArgumentException("Unknown StudentGrade name: " + name);
        };
    }

    @Override
    public String toString() {
        return name;
    }
}
