package com.BootProject.Project.Repository;

import com.BootProject.Project.Models.EmailToken;
import com.BootProject.Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken,Long> {
     EmailToken findByEmail(String email);
     EmailToken findByUuid(String uuid);
}
