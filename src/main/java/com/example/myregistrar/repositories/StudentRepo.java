package com.example.myregistrar.repositories;

import com.example.myregistrar.exceptions.StudentNotFoundException;
import com.example.myregistrar.models.Student;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepo {
    private final EntityManager entityManager;

    public boolean existsStudentByFirstNameAndLastName(String firstName, String lastName) {
        return findStudentByFirstNameAndLastName(firstName, lastName).isPresent();
    }

    public List<Student> findAll() {
        String query = "SELECT s FROM " + Student.class.getSimpleName() + " s";
        return entityManager.createQuery(query, Student.class).getResultList();
    }

    public List<Student> findStudentsByFirstName(String firstName) {
        String query = "SELECT s FROM " + Student.class.getSimpleName()
                + " s WHERE s.firstName = ?1";
        return entityManager.createQuery(query, Student.class)
                .setParameter(1, firstName)
                .getResultList();
    }

    public List<Student> findStudentsByLastName(String lastName) {
        String query = "SELECT s FROM " + Student.class.getSimpleName()
                + " s WHERE s.lastName = ?1";
        return entityManager.createQuery(query, Student.class)
                .setParameter(1, lastName)
                .getResultList();
    }

    public Optional<Student> findStudentByFirstNameAndLastName(String firstName, String lastName) {
        String query = "SELECT s FROM " + Student.class.getSimpleName()
                + " s WHERE s.firstName = :firstName AND s.lastName = :lastName";
        return entityManager.createQuery(query, Student.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Student> findStudentsByCourseId(Long courseId) {
        String query = "SELECT s FROM " + Student.class.getSimpleName()
                + " s JOIN s.courses c WHERE c.id = :courseId";
        return entityManager.createQuery(query, Student.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public Optional<Student> findStudentById(Long id) {
        Student student = entityManager.find(Student.class, id);
        return Optional.of(student);
    }

    @Modifying
    public void deleteStudent(Student student) {
        if (student.getId() == null) {
            throw new StudentNotFoundException("There is no student with such properties in database");
        } else {
            entityManager.remove(student);
            entityManager.flush();
        }
    }

    @Modifying
    public void save(Student student) {
        if (student.getId() == null) {
            entityManager.persist(student);
        } else {
            entityManager.merge(student);
        }
    }
}
