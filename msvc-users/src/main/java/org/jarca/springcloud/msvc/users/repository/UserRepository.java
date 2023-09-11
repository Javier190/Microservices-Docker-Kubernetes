package org.jarca.springcloud.msvc.users.repository;

import org.jarca.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
