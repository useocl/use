package org.tzi.use.graphql.services;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.tzi.use.model.Attribute;
import org.tzi.use.model.UseClass;
import org.tzi.use.model.Operation;
import org.tzi.use.repository.ClassRepo;

import java.util.List;

@Controller
@Service
public class MClassGService {
    private final ClassRepo classRepo;

    public MClassGService(ClassRepo repo) {
        this.classRepo = repo;
    }

    @QueryMapping
    public Iterable<UseClass> classes() {
        return classRepo.findAll();
    }

    @MutationMapping
    public UseClass aUseClass(@Argument MClassInput mClassInput) {
        UseClass tmpUseClass = new UseClass(mClassInput.name_mclass, mClassInput.attributes,mClassInput.operations);
        return classRepo.save(tmpUseClass);
    }

    private record MClassInput(String name_mclass, List<Attribute> attributes, List<Operation> operations) {}
}
