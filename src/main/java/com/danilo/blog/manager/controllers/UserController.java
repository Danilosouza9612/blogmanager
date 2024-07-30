package com.danilo.blog.manager.controllers;

import com.danilo.blog.manager.dto.user.UserRequestDTO;
import com.danilo.blog.manager.dto.user.UserResponseDTO;
import com.danilo.blog.manager.models.User;
import com.danilo.blog.manager.service.store.UserService;
import com.danilo.blog.manager.utils.Sorter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController{
    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> show(@PathVariable("id") long id){
        return service.read(id).map(user -> new ResponseEntity<>(new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getUsername()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "per_page", defaultValue = "3") int perPage,
            @RequestParam(value = "sort_column", defaultValue = "created_at") String sortColumn,
            @RequestParam(value = "sort_direction", defaultValue = "desc") String sortDirection
    ){
        return new ResponseEntity<>(StreamSupport.stream(service.list(page, perPage, new Sorter(sortColumn, sortDirection)).spliterator(), true)
                    .map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getUsername()))
                    .toList(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO user){
        User userToCreate = new User();
        userToCreate.setEmail(user.getEmail());
        userToCreate.setName(user.getName());
        userToCreate.setUsername(user.getUsername());

        User createdUser = service.create(userToCreate);
        return new ResponseEntity<>(new UserResponseDTO(createdUser.getId(), createdUser.getName(), createdUser.getEmail(), createdUser.getUsername()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") long id, @RequestBody @Valid UserRequestDTO userRequestDTO) throws InstanceNotFoundException {
        Optional<User> userOptional = service.read(id);
        User userToUpdate;
        if(userOptional.isPresent()){
            userToUpdate = userOptional.get();
            setUserFromDTO(userToUpdate, userRequestDTO);
            User updatedUser = service.update(userToUpdate);
            return new ResponseEntity<>(new UserResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(), updatedUser.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setUserFromDTO(User user, UserRequestDTO userRequestDTO){
        user.setName(userRequestDTO.getName());
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
    }

}
