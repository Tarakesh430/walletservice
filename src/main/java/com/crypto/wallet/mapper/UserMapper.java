package com.crypto.wallet.mapper;

import com.crypto.wallet.entity.User;
import com.crypto.wallet.request.CreateUserRequest;
import com.crypto.wallet.response.UserDetails;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
   User toUser(CreateUserRequest createUserRequest);
   UserDetails toUserDetails(User user);
}
