package com.example.reservasSpring.domain.mapper;


import com.example.reservasSpring.domain.dto.user.UserCreateDto;
import com.example.reservasSpring.domain.dto.user.UserGetItemDto;
import com.example.reservasSpring.domain.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel  = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User userCreateToUser(UserCreateDto userCreateDto);
    @InheritInverseConfiguration
    UserCreateDto  userToUserCreate( User user);

    UserGetItemDto userToUserItem(User user);


    List<UserGetItemDto> userToUserItemList(List<User> UserList);



}
