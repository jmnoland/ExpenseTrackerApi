package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
import com.jmnoland.expensetrackerapi.mapping.UserMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import com.jmnoland.expensetrackerapi.models.entities.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService classUnderTest;
    @Mock
    private UserRepositoryInterface userRepositoryInterface;
    @Mock
    private UserMapper userMapper;

    @Before
    public void Setup() {
        classUnderTest = new UserService(userRepositoryInterface, userMapper);
    }

    // get tests
    @Test
    public void GetUsers_ShouldNotBeEmpty_WhenThereAreExistingUsers() {
        User existing = new User("1","test","test@email.com","pass");
        List<User> existingList = new ArrayList<>();
        existingList.add(existing);
        List<UserDto> outputUserList = UserMapper.INSTANCE.entityToDto(existingList);
        when(userMapper.entityToDto(existingList)).thenReturn(outputUserList);
        when(userRepositoryInterface.getUsers()).thenReturn(existingList);

        List<UserDto> userDtos = classUnderTest.getUsers();

        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        verify(userRepositoryInterface, times(1)).getUsers();
    }

    @Test
    public void GetUser_ShouldNotBeNull_WhenUserExists() {
        User existing = new User("1","test","test@email.com","pass");
        UserDto outputUser = UserMapper.INSTANCE.entityToDto(existing);
        when(userMapper.entityToDto(existing)).thenReturn(outputUser);
        when(userRepositoryInterface.getUser("1")).thenReturn(Optional.of(existing));

        Optional<UserDto> userDto = classUnderTest.getUser("1");

        assertTrue(userDto.isPresent());
        assertEquals("test", userDto.get().name);
        verify(userRepositoryInterface, times(1)).getUser("1");
    }

    @Test
    public void GetUser_ShouldBeNull_WhenUserDoesNotExist() {
        when(userRepositoryInterface.getUser("1")).thenReturn(null);

        Optional<UserDto> userDto = classUnderTest.getUser("1");

        assertFalse(userDto.isPresent());
        verify(userRepositoryInterface, times(1)).getUser("1");
    }

    // insert tests
    @Test
    public void InsertUser_ShouldCreateUser_WhenUserIsValid() {
        UserDto createUserDto = new UserDto("1", "test", "test@email.com", "pass");
        List<User> existing = new ArrayList<>();
        User outputUser = UserMapper.INSTANCE.dtoToEntity(createUserDto);
        when(userMapper.dtoToEntity(createUserDto)).thenReturn(outputUser);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.insert(createUserDto);

        assertTrue(response.successful);
        verify(userRepositoryInterface, times(1)).insert(outputUser);
    }

    @Test
    public void InsertUser_ShouldNotCreateUser_WhenUserNameAlreadyExists() {
        UserDto createUserDto = new UserDto("1", "test", "test@email.com", "pass");
        List<User> existing = new ArrayList<>();
        existing.add(new User("1", "test", "test2@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(createUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.insert(createUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("A user with this name already exists", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).insert(outputUser);
    }

    @Test
    public void InsertUser_ShouldNotCreateUser_WhenUserEmailAlreadyExists() {
        UserDto createUserDto = new UserDto("1", "test", "test@email.com", "pass");
        List<User> existing = new ArrayList<>();
        existing.add(new User("1", "test", "test@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(createUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.insert(createUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("A user with this email already exists", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).insert(outputUser);
    }

    @Test
    public void InsertUser_ShouldNotCreateUser_WhenUserPasswordIsNull() {
        UserDto createUserDto = new UserDto("1", "test", "test@email.com", null);
        List<User> existing = new ArrayList<>();
        User outputUser = UserMapper.INSTANCE.dtoToEntity(createUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.insert(createUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("User password is required", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).insert(outputUser);
    }

    // update tests
    @Test
    public void UpdateUser_ShouldUpdateUser_WhenUserIsValid() {
        UserDto updateUserDto = new UserDto("1", "test", "test2@email.com", "pass");
        List<User> existing = new ArrayList<>();
        existing.add(new User("1", "test", "test@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(updateUserDto);
        when(userMapper.dtoToEntity(updateUserDto)).thenReturn(outputUser);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.update(updateUserDto);

        assertTrue(response.successful);
        verify(userRepositoryInterface, times(1)).update(outputUser);
    }

    @Test
    public void UpdateUser_ShouldNotUpdateUser_WhenUserDoesNotExists() {
        UserDto updateUserDto = new UserDto("1", "test", "test@email.com", "pass");
        List<User> existing = new ArrayList<>();
        existing.add(new User("2", "test", "test2@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(updateUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.update(updateUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("A user with this name already exists", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).update(outputUser);
    }

    @Test
    public void UpdateUser_ShouldNotUpdateUser_WhenUserNameAlreadyExists() {
        UserDto updateUserDto = new UserDto("1", "test", "test@email.com", "pass");
        List<User> existing = new ArrayList<>();
        existing.add(new User("2", "test", "test2@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(updateUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.update(updateUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("A user with this name already exists", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).update(outputUser);
    }

    @Test
    public void UpdateUser_ShouldNotUpdateUser_WhenUserEmailAlreadyExists() {
        UserDto updateUserDto = new UserDto("1", "test", "test@email.com", "pass");
        List<User> existing = new ArrayList<>();
        existing.add(new User("2", "test", "test@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(updateUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.update(updateUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("A user with this email already exists", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).update(outputUser);
    }

    @Test
    public void UpdateUser_ShouldNotUpdateUser_WhenUserPasswordIsNull() {
        UserDto updateUserDto = new UserDto("1", "test", "test@email.com", null);
        List<User> existing = new ArrayList<>();
        existing.add(new User("1", "test", "test@email.com", "pass"));
        User outputUser = UserMapper.INSTANCE.dtoToEntity(updateUserDto);
        when(userRepositoryInterface.getUsers()).thenReturn(existing);

        ServiceResponse<UserDto> response = classUnderTest.update(updateUserDto);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals("User password is required", response.validationErrors.get(0).message);
        verify(userRepositoryInterface, never()).update(outputUser);
    }
}
