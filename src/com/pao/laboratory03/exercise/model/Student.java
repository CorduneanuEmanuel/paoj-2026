package com.pao.laboratory03.exercise.model;
import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    int age;
    Map<Subject, Double> grades;
    public Student(String name, int age){
        grades= new HashMap<>();
        if (age<18 || age>60){
            throw new InvalidStudentException("Exceptie Student invalid");
        }
        this.name=name;
        this.age=age;
    }
    public String getName(){
        return name;

    }
    public int getAge(){
        return age;

    }
    public Map<Subject, Double> getGrades(){
        return grades;
    }
    public void addGrade(Subject subject, double grade){
        if(grade<1 || grade >10){
            throw new InvalidGradeException("Nota pusa gresit");
        }
        this.grades.put(subject, grade);
    }
    public double getAverage() {
        int suma=0;
        for(var a : this.grades.values()){
            suma+=a;
        }
        if (this.grades.isEmpty())return 0;
        return (double) suma / this.grades.size();
    }

    @Override
    public String toString(){
        return "Student{name="+this.name+", age="+this.age+", avg="+this.getAverage()+"}";
    }
}
