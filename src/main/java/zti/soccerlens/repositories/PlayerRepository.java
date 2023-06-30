package zti.soccerlens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.soccerlens.model.Player;
import zti.soccerlens.model.Team;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>
{
	List<Player> findAllByTeam(Team team);
}
