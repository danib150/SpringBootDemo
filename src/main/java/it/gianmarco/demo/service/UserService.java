package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        try {
            logger.info("userService | findAll: {}",new ObjectMapper().writeValueAsString(users));
        } catch (JsonProcessingException e) {
            logger.warn("Impossibile convertire l'oggetto in stringa!");
        }
        return users;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
