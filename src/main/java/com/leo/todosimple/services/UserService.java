package com.leo.todosimple.services;

import com.leo.todosimple.models.User;
import com.leo.todosimple.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado. Id " + id + " Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new RuntimeException("Não é possível deletar pois há entidades relacionadas!");
        }
    }

}
