package org.tzi.use.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tzi.use.rest.services.ClassService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/class/{className}")
    @GetMapping("/class/attributes")
    @GetMapping("/class/operations")
    @GetMapping("/class/preposts")

    @PostMapping("/class/{className}/attribute")
    @PostMapping("/class/{className}/operation")
    @PostMapping("/class/{className}/prepost")

}
