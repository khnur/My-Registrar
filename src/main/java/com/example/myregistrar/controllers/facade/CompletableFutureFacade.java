package com.example.myregistrar.controllers.facade;

import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.dtos.auth_dto.JwtDto;
import com.example.myregistrar.dtos.auth_dto.ResponseDto;
import com.example.myregistrar.dtos.auth_dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CompletableFutureFacade {
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseUrl;

    public ResponseEntity<List<Object>> callConcurrentEndpoints(Long id) {
        final String[] endpointAddresses = endpointAddressesOfStudent(id);
        List<CompletableFuture<Object>> futures = callEndpointAsync(endpointAddresses);

        CompletableFuture<Void> futureAll = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        futureAll.join();

        List<Object> res = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        return ResponseEntity.ok(res);
    }

    private List<CompletableFuture<Object>> callEndpointAsync(String[] endpointAddresses) {
        return Stream.of(endpointAddresses)
                .map(endpoint -> CompletableFuture.supplyAsync(
                        () -> fetchDataFromEndpoint(endpoint)
                ))
                .toList();
    }


    private Object fetchDataFromEndpoint(String endpoint) {
        ResponseEntity<Object> response = restTemplate.getForEntity(endpoint, Object.class);
        return response.getBody();
    }

    private String[] endpointAddressesOfStudent(Long studentId) {
        final String base = baseUrl + "/student/" + studentId;
        return new String[]{
                base,
                base + "/uni",
                base + "/course",
                base + "/book",
                base + "/report"
        };
    }

    private String getTokenFromUser(UserDto userDto) {
        final String endpoint = baseUrl + "/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto, headers);

        ResponseEntity<ResponseDto> responseEntity = restTemplate.postForEntity(endpoint, requestEntity, ResponseDto.class);

        ResponseDto<JwtDto> body = responseEntity.getBody();
        if (responseEntity.getStatusCode().is2xxSuccessful() && body != null) {
            JwtDto jwtDto = body.getObject();
            if (jwtDto != null) {
                return jwtDto.getJwt();
            }
        }
        return null;
    }
}
