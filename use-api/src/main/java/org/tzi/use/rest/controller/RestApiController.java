package org.tzi.use.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tzi.use.api.UseApiException;
import org.tzi.use.DTO.ClassDTO;
import org.tzi.use.rest.services.MClassRService;
import org.tzi.use.uml.mm.MInvalidModelException;

import java.util.List;

@RestController
//@RequestMapping("/api") guidelines
public class RestApiController {

    @Autowired
    private MClassRService mClassService;


    @GetMapping("/classes")
    public ResponseEntity<List<ClassDTO>> getMClasses() {
        return ResponseEntity.ok(mClassService.getAllMCLass());
    }

    @PostMapping("/class")
    public ResponseEntity<Object> createMClass(@RequestBody ClassDTO aClassDTO) {
        try {
            ClassDTO savedClassDTO = mClassService.saveMClass(aClassDTO);
            return new ResponseEntity<>(savedClassDTO, HttpStatus.CREATED);
        }catch (MInvalidModelException e) {
            // Return a detailed error message for MInvalidModelException
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UseApiException e) {
            // Return a detailed error message for UseApiException
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
