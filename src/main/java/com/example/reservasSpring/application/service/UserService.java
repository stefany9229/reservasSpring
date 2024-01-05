package com.example.reservasSpring.application.service;

import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.dto.user.UserGetItemDto;
import com.example.reservasSpring.domain.mapper.UserMapper;
import com.example.reservasSpring.domain.model.User;
import com.example.reservasSpring.domain.model.lasting.ERole;
import com.example.reservasSpring.domain.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private IUserRepository userRepository;
    private UserMapper userMapper;

    public UserGetItemDto create(UserCreateDto userCreateDto){

        //User user=  userRepository.save(userMapper.userCreateToUser(userCreateDto));
        User user = userMapper.userCreateToUser(userCreateDto);
        //user.setRoles(List.of(ERole.CLIENT));
        User userResponse= userRepository.save(user);
        return  userMapper.userToUserItem(userResponse);
    }

    public List<UserGetItemDto> findAll(){
        return userMapper.userToUserItemList(userRepository.findAll());
    }

    public List<UserGetItemDto> findAllByRole(ERole role ){
        List<User> users= userRepository.findUsersWithOnlyRole(role);
        return userMapper.userToUserItemList(users);
    }
    public User findByEmail( String email){
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "No existe e usuario asociaciado al correo" + email));
    }
}
