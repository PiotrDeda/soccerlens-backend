package zti.soccerlens.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zti.soccerlens.model.MatchEvent;
import zti.soccerlens.repositories.MatchEventRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/match-events")
public class MatchEventController
{
	private final MatchEventRepository matchEventRepository;

	public MatchEventController(MatchEventRepository matchEventRepository)
	{
		this.matchEventRepository = matchEventRepository;
	}

	@GetMapping
	public List<MatchEvent> getAllMatchEvents()
	{
		return matchEventRepository.findAll();
	}

	@GetMapping("/{id}")
	public MatchEvent getMatchEventById(@PathVariable Long id)
	{
		return matchEventRepository.findById(id).orElse(null);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public MatchEvent addMatchEvent(@RequestBody MatchEvent matchEvent)
	{
		return matchEventRepository.save(matchEvent);
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public MatchEvent updateMatchEvent(@RequestBody MatchEvent matchEvent)
	{
		return matchEventRepository.save(matchEvent);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteMatchEvent(@PathVariable Long id)
	{
		matchEventRepository.deleteById(id);
	}
}
