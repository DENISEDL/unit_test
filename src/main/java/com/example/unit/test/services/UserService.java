package com.example.unit.test.services;

import com.example.unit.test.entities.User;
import com.example.unit.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    //  C
    public User createUser(User user){
        return userRepository.save(user);
    }
    //  R
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public Optional<User> getUserId(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser;
    }
    //  U
    public Optional<User> updateUser(Long id, User user){
        Optional<User> userUpdate = userRepository.findById(id);
        if(userUpdate.isPresent()){
            userUpdate.get().setAge(user.getAge());
            userUpdate.get().setCognome(user.getCognome());
            userUpdate.get().setEmail(user.getEmail());
            userUpdate.get().setNome(user.getNome());
            User userUp = userRepository.save(userUpdate.get());
        }else{
            return Optional.empty();
        }
        return userUpdate;
    }
    //  D
    public Optional<User> deleteUser(Long id){
        Optional<User> delete = userRepository.findById(id);
        if(delete.isPresent()){
            userRepository.delete(delete.get());
        }else{
            return Optional.empty();
        }
        return delete;
    }
}
