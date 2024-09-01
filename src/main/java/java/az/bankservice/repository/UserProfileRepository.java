package java.az.bankservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.az.bankservice.entities.UserProfile;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Page<UserProfile> findAll(Specification<UserProfile> specifications, Pageable pageRequest);
    boolean existsByPhoneNumber(String phoneNumber);
}
