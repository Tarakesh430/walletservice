package com.crypto.wallet.helper;

import com.crypto.wallet.entity.User;
import com.crypto.wallet.mapper.UserMapper;
import com.crypto.wallet.request.CreateUserRequest;
import com.crypto.wallet.utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserHelper {
    private final Logger logger= LoggerFactory.getLogger(UserHelper.class);
    private final UserMapper userMapper;

    public User createUserObject(CreateUserRequest createUserRequest){
        User user = userMapper.toUser(createUserRequest);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setLimited(CommonConstants.NOT_LIMITED);
        return user;
    }
}
