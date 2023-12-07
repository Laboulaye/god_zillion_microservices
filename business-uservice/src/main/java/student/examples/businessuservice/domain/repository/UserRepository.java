package student.examples.businessuservice.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import student.examples.businessuservice.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	

}
