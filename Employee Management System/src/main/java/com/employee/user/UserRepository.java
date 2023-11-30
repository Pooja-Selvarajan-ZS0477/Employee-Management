package com.employee.user;







import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    @Query("")
    User findByUsername(String username);
}
