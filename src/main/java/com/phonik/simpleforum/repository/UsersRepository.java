package com.phonik.simpleforum.repository;

import com.phonik.simpleforum.users.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<GeneralUser, String> {

    /**
     * query is based on method name
     * */
    Optional<GeneralUser> findByUserName(String userName);
}
