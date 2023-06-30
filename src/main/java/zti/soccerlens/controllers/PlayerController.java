package zti.soccerlens.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zti.soccerlens.model.Player;
import zti.soccerlens.repositories.PlayerRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/players")
public class PlayerController
{
	private final PlayerRepository playerRepository;

	public PlayerController(PlayerRepository playerRepository)
	{
		this.playerRepository = playerRepository;
	}

	@GetMapping
	public List<Player> getAllPlayers()
	{
		return playerRepository.findAll();
	}

	@GetMapping("/{id}")
	public Player getPlayerById(@PathVariable Long id)
	{
		return playerRepository.findById(id).orElse(null);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Player addPlayer(@RequestBody Player player)
	{
		return playerRepository.save(player);
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Player updatePlayer(@RequestBody Player player)
	{
		return playerRepository.save(player);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deletePlayer(@PathVariable Long id)
	{
		playerRepository.deleteById(id);
	}
}
