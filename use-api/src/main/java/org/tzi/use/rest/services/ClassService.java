package org.tzi.use.rest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tzi.use.repository.ClassRepo;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;
}
