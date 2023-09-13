package org.jarca.springcloud.msvc.users.controller;


import org.jarca.springcloud.msvc.users.models.entity.User;
import org.jarca.springcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

//metodo creado por mi. Deberia devolver un User pero arriba se hico el cambio dse que devolviera una ResponseEntity
    /*@GetMapping("/user/{id}")
    public User getsUserById(@PathVariable Long id){
        Optional <User> userOptional = userService.porId(id);
        return userOptional.orElse(null);
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){

        return userService.guardar(user);
    }

    /*
    //metodo creado por mi. Deberia devolver un User pero arriba se hico el cambio dse que devolviera una ResponseEntity
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createdUser(@RequestBody User user){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.guardar(user));
    }*/


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable Long id){
        Optional<User> OptFromDB = userService.porId(id);

        if (OptFromDB.isPresent()){
            User userDB = OptFromDB.get();
            userDB.setName(user.getName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.guardar(userDB));
        }
        return ResponseEntity.notFound().build();
    }

}
