package com.pao.laboratory03.exercise.service;
import com.pao.laboratory03.exercise.*;
import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;

public class StudentService {

    private static StudentService instance;

    List<Student> students;

    private StudentService(){
        this.students=new ArrayList<>();
    }

    public static StudentService getInstance(){
        if(instance==null){
            instance= new StudentService();

        }
        return instance;
    }

    public void addStudent(String name, int age){
        for (var a : this.students){
            if(Objects.equals(a.getName(), name)){
                throw new RuntimeException("Exista deja un student cu acest nume");
            }
        }
        students.add(new Student(name, age));
    }
    public Student findbyName(String name){
        for(var a : students){
            if(Objects.equals(a.getName(), name))return a;
        }
        throw new StudentNotFoundException("Student de negasit");
    }
    public void addGrade(String studentName, Subject subject, double grade){
        if(grade<1 || grade >10) throw new InvalidGradeException("Nota introdusa gresit");
        Student b;
        b=this.findbyName(studentName);

        b.addGrade(subject, grade);
    }
    public void printAllStudents(){
        for (var a : students){
            System.out.println(a.getName()+" are notele: "+ a.getGrades());
        }

    }

    public void printTopStudents(){
        students.sort(Comparator.comparingDouble(Student::getAverage));
        for(var a : students){
            System.out.println(a.getName()+" "+a.getAverage());
        }
    }
    public Map<Subject, Double> getAveragePerSubject(){
        Map<Subject, Double> suma = new HashMap<Subject, Double>();
        Map<Subject, Integer> contor = new HashMap<Subject, Integer>();
        for(var a : Subject.values()){
                suma.putIfAbsent(a, 0.0);
                contor.putIfAbsent(a, 0);
        }
        for(var a: this.students){
            for(var b: a.getGrades().entrySet()){
                suma.put(b.getKey(), b.getValue()+suma.get(b.getKey()));
                contor.put(b.getKey(), contor.get(b.getKey())+1);
            }

        }
        for( var a : suma.entrySet()){
            suma.put(a.getKey(), (double)a.getValue()/contor.get(a.getKey()));

        }
        for(var a : suma.entrySet()) {
            System.out.println(a.getKey() + " " + a.getValue());
        }
        return suma;
    }

}
