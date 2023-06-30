package zti.soccerlens.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zti.soccerlens.api.CommentResponse;
import zti.soccerlens.model.Comment;
import zti.soccerlens.model.Match;
import zti.soccerlens.model.MatchEvent;
import zti.soccerlens.repositories.CommentRepository;
import zti.soccerlens.repositories.MatchEventRepository;
import zti.soccerlens.repositories.MatchRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/matches")
public class MatchController
{
	private final MatchRepository matchRepository;
	private final MatchEventRepository matchEventRepository;
	private final CommentRepository commentRepository;

	public MatchController(MatchRepository matchRepository, MatchEventRepository matchEventRepository,
						   CommentRepository commentRepository)
	{
		this.matchRepository = matchRepository;
		this.matchEventRepository = matchEventRepository;
		this.commentRepository = commentRepository;
	}

	@GetMapping
	public List<Match> getAllMatches()
	{
		return matchRepository.findAll();
	}

	@GetMapping("/{id}")
	public Match getMatchById(@PathVariable Long id)
	{
		return matchRepository.findById(id).orElse(null);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Match addMatch(@RequestBody Match match)
	{
		return matchRepository.save(match);
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Match updateMatch(@RequestBody Match match)
	{
		return matchRepository.save(match);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteMatch(@PathVariable Long id)
	{
		matchRepository.deleteById(id);
	}

	@GetMapping("/{id}/events")
	public List<MatchEvent> getEventsByMatchId(@PathVariable Long id)
	{
		return matchEventRepository.findAllByMatch(matchRepository.findById(id).orElse(null));
	}

	@GetMapping("/{id}/comments")
	public List<CommentResponse> getCommentsByMatchId(@PathVariable Long id)
	{
		return commentRepository.findAllByMatch(matchRepository.findById(id).orElse(null)).stream()
				.map(Comment::mapToResponse).toList();
	}
}
