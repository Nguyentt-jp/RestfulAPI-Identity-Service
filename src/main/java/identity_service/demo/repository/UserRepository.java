package identity_service.demo.repository;

import identity_service.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

   boolean existsUserByUserName(String userName);
}
