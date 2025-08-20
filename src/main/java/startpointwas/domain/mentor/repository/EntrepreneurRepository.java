package startpointwas.domain.mentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import startpointwas.domain.mentor.entity.Entrepreneur;

public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, Long> {
}