package com.example.myregistrar.controllers;

import com.example.myregistrar.annotation.LogDuration;
import com.example.myregistrar.controllers.facade.CompletableFutureFacade;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.auth_dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/completable-future")
@RequiredArgsConstructor
public class CompletableFutureController {
    private final CompletableFutureFacade completableFutureFacade;

    @LogDuration
    @GetMapping("/{id}") // Student id
    public ResponseEntity<List<Object>> callConcurrentEndpoints(@PathVariable Long id) {

        return completableFutureFacade.callConcurrentEndpoints(id);
    }

//    @LogDuration
//    @PostMapping
//    public ResponseEntity<StudentDto> postEntity(@RequestBody StudentDto studentDto) {
//        return completableFutureFacade.postStudentAsync(studentDto);
//    }
}
