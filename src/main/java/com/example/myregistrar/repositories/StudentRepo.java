package com.example.myregistrar.repositories;

import com.example.myregistrar.models.Student;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class StudentRepo implements QueryByExampleExecutor<Student> {
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
                + " s WHERE s.first_name = ?1";
        return entityManager.createQuery(query, Student.class)
                .setParameter(1, firstName)
                .getResultList();
    }

    public List<Student> findStudentsByLastName(String lastName) {
        String query = "SELECT s FROM " + Student.class.getSimpleName()
                + " s WHERE s.last_name = ?1";
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

    public <S extends Student> void save(S student) {
        if (student.getId() == null) {
            entityManager.persist(student);
        } else {
            entityManager.merge(student);
        }
    }

    @Override
    public <S extends Student> Optional<S> findOne(Example<S> example) {
        S student = entityManager.find(example.getProbeType(), example.getProbe().getId());
        return Optional.ofNullable(student);
    }

    @Override
    public <S extends Student> Iterable<S> findAll(Example<S> example) {
        String query = "SELECT * FROM " + example.getProbeType().getSimpleName();
        return entityManager.createQuery(query, example.getProbeType()).getResultList();
    }

    @Override
    public <S extends Student> Iterable<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Student> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Student> long count(Example<S> example) {
        String query = "SELECT count(*) FROM " + example.getProbeType().getSimpleName();
        return entityManager.createQuery(query, long.class)
                .getSingleResult();
    }

    @Override
    public <S extends Student> boolean exists(Example<S> example) {
        return entityManager.find(example.getProbeType(), example.getProbe().getId()) != null;
    }

    @Override
    public <S extends Student, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
