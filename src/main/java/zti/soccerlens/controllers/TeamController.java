package zti.soccerlens.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zti.soccerlens.model.Match;
import zti.soccerlens.model.Player;
import zti.soccerlens.model.Team;
import zti.soccerlens.repositories.MatchRepository;
import zti.soccerlens.repositories.PlayerRepository;
import zti.soccerlens.repositories.TeamRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teams")
public class TeamController
{
	private final TeamRepository teamRepository;
	private final PlayerRepository playerRepository;
	private final MatchRepository matchRepository;

	public TeamController(TeamRepository teamRepository, PlayerRepository playerRepository,
						  MatchRepository matchRepository)
	{
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
		this.matchRepository = matchRepository;
	}

	@GetMapping
	public List<Team> getAllTeams()
	{
		return teamRepository.findAll();
	}

	@GetMapping("/{id}")
	public Team getTeamById(@PathVariable Long id)
	{
		return teamRepository.findById(id).orElse(null);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Team addTeam(@RequestBody Team team)
	{
		return teamRepository.save(team);
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Team updateTeam(@RequestBody Team team)
	{
		return teamRepository.save(team);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteTeam(@PathVariable Long id)
	{
		teamRepository.deleteById(id);
	}

	@GetMapping("/{id}/players")
	public List<Player> getPlayersByTeamId(@PathVariable Long id)
	{
		return playerRepository.findAllByTeam(teamRepository.findById(id).orElse(null));
	}

	@GetMapping("/{id}/matches")
	public List<Match> getMatchesByTeamId(@PathVariable Long id)
	{
		return matchRepository.findAllByHomeTeamOrAwayTeam(teamRepository.findById(id).orElse(null),
				teamRepository.findById(id).orElse(null));
	}
}
