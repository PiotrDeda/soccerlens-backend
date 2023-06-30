package zti.soccerlens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zti.soccerlens.model.Match;
import zti.soccerlens.model.Player;
import zti.soccerlens.model.Team;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>
{
	List<Match> findAllByHomeTeamOrAwayTeam(Team homeTeam, Team awayTeam);
}
