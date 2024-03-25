package com.example.unit.test.controllers;

import com.example.unit.test.entities.User;
import com.example.unit.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(userService.createUser(user));
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }
    @GetMapping("/getUserId/{id}")
    public ResponseEntity<User> getUserId(@PathVariable Long id){
        Optional<User> optionalUser = userService.getUserId(id);
        return ResponseEntity.ok().body(optionalUser.get());
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUserId(@PathVariable Long id,@RequestBody User user){
        Optional<User> userUpdate = userService.updateUser(id,user);
        if(userUpdate.isPresent()){
            return ResponseEntity.ok().body(userUpdate.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        Optional<User> deleteU = userService.deleteUser(id);
        if(deleteU.isPresent()){
            return ResponseEntity.ok().body(deleteU.get());
        }
        return ResponseEntity.notFound().build();
    }
}
