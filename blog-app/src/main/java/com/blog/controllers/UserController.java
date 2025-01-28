package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    //update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
    	UserDto updateUser = this.userService.updateUser(userDto, userId);
    	return ResponseEntity.ok(updateUser);
    }
    
    //delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        ApiResponse response = new ApiResponse("User deleted successfully", true); // Response message
        return new ResponseEntity<>(response, HttpStatus.OK); // Return ResponseEntity
    }
    
    //get single user
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        UserDto user = this.userService.getUserById(userId); 
        return ResponseEntity.ok(user); // Return response with user data
    }
    
    //get all user
     @GetMapping("/")
     public ResponseEntity<List<UserDto>> getAllUsers() {
    	return ResponseEntity.ok(this.userService.getAllUser());
    }
}
