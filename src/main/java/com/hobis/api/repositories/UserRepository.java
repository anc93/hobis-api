package com.hobis.api.repositories;
import com.hobis.api.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer>
{
//    void delete(Optional<Users> userToDelete);
//    void delete(Optional<Users> userToUpdate);
}
