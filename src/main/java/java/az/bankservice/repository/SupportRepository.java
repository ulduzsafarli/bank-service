package java.az.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.az.bankservice.entities.Support;
import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long> {

    @Query(value = "SELECT s FROM Support s WHERE s.isAnswered = false")
    List<Support> findUnAnsweredRequests();
}
