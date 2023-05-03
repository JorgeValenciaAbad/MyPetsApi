package it.jorge.protectora.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.jorge.protectora.Model.LostAnimal;
import it.jorge.protectora.Service.LostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static it.jorge.protectora.util.Constants.route;

@RestController
@Tag(name = "Lost Controller")
@RequestMapping("/api/pet/complaint")
public class LostController {

    @Autowired
    LostService lostService;

    @GetMapping
    public ResponseEntity<?> getComplaints(){
        try{
            return ResponseEntity.ok(lostService.findAll());
        }catch (Exception e){
            return new ResponseEntity("CONFLICT", HttpStatus.CONFLICT);
        }
    }
    @PostMapping
    public ResponseEntity<?> addComplaint(@RequestParam("image")MultipartFile image, @RequestParam("summary") String summary){
        try{
            Files.write(Paths.get(route+"//"+image.getOriginalFilename()),image.getBytes());
            return ResponseEntity.ok(lostService.save(new LostAnimal(summary,image.getOriginalFilename())));
        }catch (Exception e){
            return new ResponseEntity("CONFLICT", HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable("id") int id){
        try{
            lostService.deleteById(id);
            return ResponseEntity.ok("DELETE COMPLAINT");
        }catch(Exception e){
            return new ResponseEntity("CONFLICT", HttpStatus.CONFLICT);
        }
    }

}
