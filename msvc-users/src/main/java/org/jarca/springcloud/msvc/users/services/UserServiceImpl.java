package org.jarca.springcloud.msvc.users.services;

import org.jarca.springcloud.msvc.users.models.entity.User;
import org.jarca.springcloud.msvc.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> listar() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> porId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User guardar(User usuario) {
        return userRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }
}
