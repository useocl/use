package org.tzi.use.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tzi.use.UseModelFacade;
import org.tzi.use.api.UseApiException;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.repository.ClassRepo;
import org.tzi.use.uml.mm.MInvalidModelException;

import java.util.List;

@Service
public class MClassRService {

    @Autowired
    private ClassRepo classRepository;

    @Deprecated
    public ClassDTO saveMClass(ClassDTO aClassDTO) throws MInvalidModelException, UseApiException {
        UseModelFacade.createMClass(aClassDTO);
//        if(classRepository.findById(aClassDTO.getName_mclass()).isPresent()) {
//            // TODO Exception already exists
//            throw new UseApiException("Class name already exists");
//        }
        return classRepository.save(aClassDTO);
    }

    public List<ClassDTO> getAllMCLass(){
        return classRepository.findAll();
    }
}