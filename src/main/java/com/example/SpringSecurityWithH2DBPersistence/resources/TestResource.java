package com.example.SpringSecurityWithH2DBPersistence.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>("this is a test, I repeat, this is a test!", HttpStatus.I_AM_A_TEAPOT);
    }

}
