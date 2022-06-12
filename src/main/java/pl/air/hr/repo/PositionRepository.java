package pl.air.hr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.air.hr.model.Position;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByName(String name);

}
