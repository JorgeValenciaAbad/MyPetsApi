package it.jorge.protectora.Controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.jorge.protectora.Model.Pet;
import it.jorge.protectora.Model.User;
import it.jorge.protectora.Service.PetService;
import it.jorge.protectora.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "Pet Controller")
@RequestMapping("/api/pet")
public class PetController {

    @Autowired
    PetService petService;


    @Operation(summary = "Get adoption pets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting animals for adoption", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "409", description = "Error when getting animals for adoption")
    })

    @GetMapping
    public List<Pet> getPets(){
        return petService.getPets();
    }
    @Operation(summary = "Get pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get animal by ID", content = @Content(schema = @Schema(implementation = Pet.class))),
            @ApiResponse(responseCode = "409", description = "Error when get animal by ID ")
    })
    @GetMapping("/{id}")
    public Pet findByIdPets (@PathVariable int id){
        return petService.findById(id).get();
    }
   

    @GetMapping("/types")
    public List<String> getTypes(){
        return petService.getPetType();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getPetsType(@PathVariable("type") String type){
        try{
            return  ResponseEntity.ok(petService.getPetsType(type));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseController.ERROR5);
        }
    }
}
