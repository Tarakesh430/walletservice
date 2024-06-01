package com.crypto.wallet.service;

import com.crypto.wallet.entity.User;
import com.crypto.wallet.entity.Wallet;
import com.crypto.wallet.helper.UserHelper;
import com.crypto.wallet.helper.WalletHelper;
import com.crypto.wallet.mapper.UserMapper;
import com.crypto.wallet.repository.UserRepository;
import com.crypto.wallet.request.CreateUserRequest;
import com.crypto.wallet.response.UserDetails;
import com.crypto.wallet.validations.Validation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service("userService")
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserHelper userHelper;
    private final WalletService walletService;
    private final WalletHelper walletHelper;
    private final UserMapper userMapper;
    @Transactional(value= Transactional.TxType.REQUIRED)
    public UserDetails createUser(CreateUserRequest createUserRequest) throws Exception {
        // Check for User Already Exists
        logger.info("New User Creation Request Received {}",createUserRequest);
        validateUniqueUser(createUserRequest);
        User newUser = userHelper.createUserObject(createUserRequest);
        Wallet wallet = walletHelper.createWallet();
        newUser.setWallet(wallet);
        newUser = userRepository.save(newUser);
        UserDetails userDetails = userMapper.toUserDetails(newUser);
        userDetails.setWalletId(newUser.getWallet().getWalletId());
        return userDetails;
    }

    private void validateUniqueUser(CreateUserRequest createUserRequest) throws Exception {
        Optional<User> user = userRepository.findByEmailId(createUserRequest.getEmailId());
        if(user.isPresent()){
            logger.info("User Details Already Exists");
            throw new Exception("User Credentials Already Present");
        }
    }

    public UserDetails getUser(String emailId) throws Exception {
        logger.info("User email {} ",emailId);
        Optional<User> user=userRepository.findByEmailId(emailId);
        if(user.isEmpty())
            logger.info("Dengindhi");
      //  logger.info("yfyg",user.get());
        return user.map(userMapper::toUserDetails).orElseThrow(()->new Exception("User Credentials Already Present Denguthadi"));
    }

}
