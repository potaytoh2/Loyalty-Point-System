package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.user.entity.Role;
import static com.user.entity.Role.*;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.payload.request.CreateUserRequest;
import com.user.payload.request.UpdateUserRequest;
import com.user.payload.request.UserRequest;
import com.user.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<Role, List<String>> map = new HashMap<>();

    @Autowired
    public UserService(UserRepository userRepository,
            @Value("#{'${role.owner}'.split(',')}") List<String> owner,
            @Value("#{'${role.manager}'.split(',')}") List<String> manager,
            @Value("#{'${role.engineer}'.split(',')}") List<String> engineer,
            @Value("#{'${role.product_manager}'.split(',')}") List<String> product_manager) {
        this.userRepository = userRepository;
        map.put(OWNER, owner);
        map.put(MANAGER, manager);
        map.put(ENGINEER, engineer);
        map.put(PRODUCT_MANAGER, product_manager);
    }

    /**
     * @param id
     * @return User
     */
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User account with id %s does not exist.", id)));
    }

    /**
     * @return Iterable<User>
     */
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * @param user
     * @return User
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * @param user
     * @return User
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * @param id
     * @return User
     */
    public User deleteUserById(String id) {
        User toReturn = getUserById(id);
        userRepository.deleteById(id); // deleteById returns void so we can't return it
        return toReturn;
    }

    public List<String> getUserPerms(String id) {
        return map.get(getUserById(id).getRole());
    }

    public User createUser(CreateUserRequest request) {
        return createUser(generateFromRequestWithId(request, request.getId()));
    }

    public User updateUser(UpdateUserRequest request, String id) {
        getUserById(id);
        return updateUser(generateFromRequestWithId(request, id));
    }

    private User generateFromRequestWithId(UserRequest request, String id) {
        Role role = convertRoleFromString(request.getRole());
        return new User(id, request.getEmail(), request.getFirst_name(), request.getLast_name(), role);
    }
}