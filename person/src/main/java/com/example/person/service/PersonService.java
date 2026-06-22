package com.example.person.service;

import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    public Optional<Person> save(Person person) {
        if (repository.findById(person.getId()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(repository.save(person));
    }
}
