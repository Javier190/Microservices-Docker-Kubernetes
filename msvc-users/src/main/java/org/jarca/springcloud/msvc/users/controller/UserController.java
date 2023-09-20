package org.jarca.springcloud.msvc.users.controller;


import org.jarca.springcloud.msvc.users.models.entity.User;
import org.jarca.springcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers (){
        return userService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id){
        Optional <User> userOptional = userService.porId(id);
        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity <?> createUser(@Valid @RequestBody User user, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return validateResult(bindingResult);
        }
        if (!user.getEmail().isEmpty() && userService.getByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje","User Already Exist in the DDBB!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.guardar(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user,BindingResult bindingResult, @PathVariable Long id){

        if (bindingResult.hasErrors()){
            return validateResult(bindingResult);
        }
        Optional<User> OptFromDB = userService.porId(id);
        if (OptFromDB.isPresent()){
            User userDB = OptFromDB.get();

            if (!user.getEmail().isEmpty() &&
                    !user.getEmail().equalsIgnoreCase(userDB.getEmail()) &&
                    userService.getByEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje","User Already Exist in the DDBB!"));
            }

            userDB.setName(user.getName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.guardar(userDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        Optional<User> userOpt = userService.porId(id);

        if (userOpt.isPresent()){
            userService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validateResult(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
