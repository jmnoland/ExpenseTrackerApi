package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.UserServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.UserMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.User;
import com.jmnoland.expensetrackerapi.validators.user.CreateUserValidator;
import com.jmnoland.expensetrackerapi.validators.user.UpdateUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import static com.jmnoland.expensetrackerapi.services.AuthenticationService.generateStrongPasswordHash;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepositoryInterface userRepository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepositoryInterface userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.mapper = userMapper;
    }

    public List<UserDto> getUsers() {
        List<User> existing = this.userRepository.getUsers();
        return this.mapper.entityToDto(existing);
    }

    public Optional<UserDto> getUser(String userId) {
        Optional<User> existing = this.userRepository.getUser(userId);
        if (existing == null || !existing.isPresent()) return Optional.empty();
        return Optional.of(this.mapper.entityToDto(existing.get()));
    }

    public Optional<UserDto> getUserByEmail(String email) {
        Optional<User> existing = this.userRepository.getUserByEmail(email);
        if (existing == null || !existing.isPresent())  return Optional.empty();
        return Optional.of(this.mapper.entityToDto(existing.get()));
    }

    public boolean userExist(String userId) {
        return this.userRepository.userExists(userId);
    }

    public ServiceResponse<UserDto> insert(UserDto userDto) {
        List<User> userList = this.userRepository.getUsers();

        List<ValidationError> validationErrors = new CreateUserValidator().validate(userDto, userList);

        User newUser = null;
        if (validationErrors.isEmpty()) {
            newUser = this.mapper.dtoToEntity(userDto);
            try {
                newUser.setPassword(generateStrongPasswordHash(userDto.password));
                newUser = this.userRepository.insert(newUser);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        newUser.setPassword(null);
        return new ServiceResponse<>(
                this.mapper.entityToDto(newUser),
                validationErrors.isEmpty(),
                validationErrors
        );
    }

    public void delete(UserDto userDto) {
        this.userRepository.delete(this.mapper.dtoToEntity(userDto));
    }

    public ServiceResponse<UserDto> update(UserDto userDto) {
        List<User> userList = this.userRepository.getUsers();

        List<ValidationError> validationErrors = new UpdateUserValidator().validate(userDto, userList);

        User newUser = null;
        if (validationErrors.isEmpty()) {
            newUser = this.mapper.dtoToEntity(userDto);
            try {
                newUser = this.userRepository.update(newUser);
            } catch (Exception exception) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(newUser),
                validationErrors.isEmpty(),
                validationErrors
        );
    }
}
