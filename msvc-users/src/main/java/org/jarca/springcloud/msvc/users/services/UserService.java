package org.jarca.springcloud.msvc.users.services;

import org.jarca.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> listar();
    Optional<User> porId(Long id);
    User guardar(User usuario);
    void eliminar(Long id);

}
