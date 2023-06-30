package zti.soccerlens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.soccerlens.model.Comment;
import zti.soccerlens.model.Match;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{
	List<Comment> findAllByMatch(Match match);
}
