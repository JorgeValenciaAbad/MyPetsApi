package it.jorge.protectora.Controller;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.jorge.protectora.Model.User;
import it.jorge.protectora.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static it.jorge.protectora.util.Functions.*;

@RestController
@Tag(name = "User Controller")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Log in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged in app", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Error when trying to connect to the application")
    })
    @PostMapping("/login")
    public ResponseEntity<?> hashLogin (@RequestBody User user){

        User login = userService.hashLogin(user.getEmail());
        if(login != null){
            if (new BCryptPasswordEncoder().matches(user.getPass(), login.getPass())){
                return ResponseEntity.ok(getJWTToken(login));
            }
        }
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);

    }
    @Operation(summary = "Get user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gets the user by token", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error when get user by Token")
    })
    @PostMapping
    public User getUser(@RequestHeader("Authorization") String jwt){
        Claims claims = getClaims(jwt);
        return userService.findById((Integer) claims.get("id")).get();
    }
    @Operation(summary = "Register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add user from data base", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error when add from data base ", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PostMapping("/register")
    public User insertUser(@RequestBody User user){

        String pass = user.getPass();
        user.setPass(encodePass(pass));
        return userService.save(user);
    }
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modifies user from data base", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error when modifies user from data base", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PutMapping
    public User updateUser(@RequestBody User user){
        return userService.save(user);
    }
    @Operation(summary = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remove user from database", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Error remove user from database")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        userService.delete(userService.findById(id).get());
    }

}
