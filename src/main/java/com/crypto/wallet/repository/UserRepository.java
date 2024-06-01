package com.crypto.wallet.repository;

import com.crypto.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
//    @Query(value = "select * from user where email_id=?",nativeQuery = true)
    Optional<User> findByEmailId(String email);
}
