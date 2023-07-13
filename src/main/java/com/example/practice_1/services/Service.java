package com.example.practice_1.services;

import com.example.practice_1.repos.StudentRepo;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    private final StudentRepo studentRepo;



}
