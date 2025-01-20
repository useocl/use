package org.tzi.use.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tzi.use.MClassFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.model.UseClass;
import org.tzi.use.repository.ClassRepo;
import org.tzi.use.uml.mm.MInvalidModelException;

import java.util.List;

@Service
public class MClassRService {

    @Autowired
    private ClassRepo classRepository;

    public UseClass saveMClass(UseClass aUseClass) throws MInvalidModelException, UseApiException {
        MClassFacade.createMClass(aUseClass);
        if(classRepository.findById(aUseClass.getName_mclass()).isPresent()) {
            // TODO Exception already exists
            throw new UseApiException("Class name already exists");
        }
        return classRepository.save(aUseClass);
    }

    public List<UseClass> getAllMCLass(){
        return classRepository.findAll();
    }
}