package com.woonsheng.chatgpt_teach_me_02.repository;


import com.woonsheng.chatgpt_teach_me_02.entity.myuser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<myuser, Long> {
    Optional<myuser> findByEmail(String email);
    Optional<myuser> findByResetToken(String resetToken);
}