package com.app.smarthome.repositories;

import com.app.smarthome.models.Role;
import com.app.smarthome.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE role.id MEMBER OF u.roles")
    List<User> findAllUsersWithRoleMemberOfRoles(Role role);

    @Query("SELECT u FROM User u WHERE houseId MEMBER OF u.houses")
    List<User> findAllUsersWithHouseIdMemberOfHouses(ObjectId houseId);

    List<User> getUserByEmailIn(List<String> emails);
}
