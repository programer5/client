package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {

    //http://localhost/api/server/hello
    //response
    public UserResponse hello() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name", "steve")
                .queryParam("age", 20)
                .encode()
                .build()
                .toUri();
        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }

    public UserResponse post() {
        //http://localhost:9090/api/server/user/{userId}/name/{userName}

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve")
                .toUri();

        System.out.println("uri = " + uri);

        //http body -> object -> object mapper -> json -> restTemplate -> http body json
        UserRequest request = new UserRequest();
        request.setName("steve");
        request.setAge(20);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, request, UserResponse.class);

        System.out.println("response = " + response.getStatusCode());
        System.out.println("response.getHeaders() = " + response.getHeaders());
        System.out.println("response.getBody() = " + response.getBody());

        return response.getBody();
    }

    public UserResponse exchange() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve")
                .toUri();

        System.out.println("uri = " + uri);

        //http body -> object -> object mapper -> json -> restTemplate -> http body json
        UserRequest request = new UserRequest();
        request.setName("steve");
        request.setAge(20);

        RequestEntity<UserRequest> requestEntity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "fffff")
                .body(request);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity, UserResponse.class);
        return response.getBody();
    }

    public Req<UserResponse> genericExchange() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve")
                .toUri();

        System.out.println("uri = " + uri);

        //http body -> object -> object mapper -> json -> restTemplate -> http body json


        UserRequest userRequest = new UserRequest();
        userRequest.setName("steve");
        userRequest.setAge(20);

        Req<UserRequest> req = new Req<>();
        req.setHeader(
            new Req.Header()
        );
        req.setBody(
            userRequest
        );

        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "fffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Req<UserResponse>> response
                = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>(){});

        return response.getBody();
    }
}
