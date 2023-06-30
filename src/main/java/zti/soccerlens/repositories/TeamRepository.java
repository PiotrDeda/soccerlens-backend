package zti.soccerlens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.soccerlens.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}
