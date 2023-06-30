package zti.soccerlens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.soccerlens.model.Match;
import zti.soccerlens.model.MatchEvent;

import java.util.List;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long>
{
	List<MatchEvent> findAllByMatch(Match match);
}
