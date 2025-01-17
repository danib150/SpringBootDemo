package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.entity.dto.UserDto;
import it.gianmarco.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PatchMapping("/{userid}")
    public ResponseEntity<User> update(@PathVariable Long userid, @RequestBody UserDto userDto) {
        if (Objects.isNull(userid)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(userService.save(userDto));
    }
}
