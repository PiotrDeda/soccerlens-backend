package zti.soccerlens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zti.soccerlens.model.*;
import zti.soccerlens.repositories.MatchEventRepository;
import zti.soccerlens.repositories.MatchRepository;
import zti.soccerlens.repositories.PlayerRepository;
import zti.soccerlens.repositories.TeamRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SoccerLensApplication implements CommandLineRunner
{
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private MatchEventRepository matchEventRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(SoccerLensApplication.class, args);
	}

	@Override
	public void run(String... args)
	{
		if (teamRepository.count() > 0)
			return;
		List<Team> teams = new ArrayList<>();
		teams.add(new Team("Manchester United"));
		teams.add(new Team("Manchester City"));
		teams.add(new Team("Liverpool"));
		teams.add(new Team("Chelsea"));
		teams.add(new Team("Arsenal"));
		teamRepository.saveAll(teams);
		List<Match> matches = new ArrayList<>();
		matches.add(new Match(teams.get(4), teams.get(0), LocalDateTime.of(2023, 6, 24, 12, 0, 0),
				LocalDateTime.of(2023, 6, 24, 13, 30, 0)));
		matches.add(new Match(teams.get(0), teams.get(1), LocalDateTime.of(2023, 6, 24, 12, 30, 0)));
		matches.add(new Match(teams.get(2), teams.get(3), LocalDateTime.of(2023, 6, 25, 12, 0, 0)));
		matchRepository.saveAll(matches);
		List<Player> players = new ArrayList<>();
		players.add(new Player(teams.get(0), "David de Gea"));
		players.add(new Player(teams.get(0), "Harry Maguire"));
		players.add(new Player(teams.get(0), "Victor Lindelof"));
		players.add(new Player(teams.get(1), "Ederson"));
		players.add(new Player(teams.get(1), "Kyle Walker"));
		players.add(new Player(teams.get(1), "Ruben Dias"));
		players.add(new Player(teams.get(2), "Alisson"));
		players.add(new Player(teams.get(2), "Trent Alexander-Arnold"));
		players.add(new Player(teams.get(2), "Virgil van Dijk"));
		players.add(new Player(teams.get(3), "Edouard Mendy"));
		players.add(new Player(teams.get(3), "Cesar Azpilicueta"));
		players.add(new Player(teams.get(3), "Thiago Silva"));
		players.add(new Player(teams.get(4), "Bernd Leno"));
		players.add(new Player(teams.get(4), "Hector Bellerin"));
		players.add(new Player(teams.get(4), "Gabriel"));
		playerRepository.saveAll(players);
		List<MatchEvent> matchEvents = new ArrayList<>();
		matchEvents.add(new MatchEvent(matches.get(0), players.get(0), EventType.GOAL, 10));
		matchEvents.add(new MatchEvent(matches.get(0), players.get(1), EventType.OWN_GOAL, 20));
		matchEvents.add(new MatchEvent(matches.get(0), players.get(2), EventType.YELLOW_CARD, 30));
		matchEvents.add(new MatchEvent(matches.get(0), players.get(3), EventType.RED_CARD, 40));
		matchEvents.add(new MatchEvent(matches.get(0), players.get(0), players.get(1), EventType.SUBSTITUTION, 50));
		matchEvents.add(new MatchEvent(matches.get(1), players.get(4), EventType.GOAL, 10));
		matchEvents.add(new MatchEvent(matches.get(1), players.get(5), EventType.OWN_GOAL, 20));
		matchEvents.add(new MatchEvent(matches.get(1), players.get(6), EventType.YELLOW_CARD, 30));
		matchEvents.add(new MatchEvent(matches.get(1), players.get(7), EventType.RED_CARD, 40));
		matchEvents.add(new MatchEvent(matches.get(1), players.get(4), players.get(5), EventType.SUBSTITUTION, 50));
		matchEvents.add(new MatchEvent(matches.get(2), players.get(8), EventType.GOAL, 10));
		matchEvents.add(new MatchEvent(matches.get(2), players.get(9), EventType.OWN_GOAL, 20));
		matchEvents.add(new MatchEvent(matches.get(2), players.get(10), EventType.YELLOW_CARD, 30));
		matchEvents.add(new MatchEvent(matches.get(2), players.get(11), EventType.RED_CARD, 40));
		matchEvents.add(new MatchEvent(matches.get(2), players.get(8), players.get(9), EventType.SUBSTITUTION, 50));
		matchEventRepository.saveAll(matchEvents);
	}
}
