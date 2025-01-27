package com.BootProject.Project.Repository;

import com.BootProject.Project.Models.Token;
import com.BootProject.Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    List<Token>findByUserid(User id);
}
