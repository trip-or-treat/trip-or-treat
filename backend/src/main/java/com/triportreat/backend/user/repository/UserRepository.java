package com.triportreat.backend.user.repository;

import com.triportreat.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}