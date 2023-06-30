package zti.soccerlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Match
{
	@Id
	@GeneratedValue
	private Long matchId;
	@ManyToOne
	@JoinColumn(name = "home_team_id")
	private Team homeTeam;
	@ManyToOne
	@JoinColumn(name = "away_team_id")
	private Team awayTeam;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	protected Match() {}

	public Match(Team homeTeam, Team awayTeam, LocalDateTime startTime)
	{
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.startTime = startTime;
	}

	public Match(Team homeTeam, Team awayTeam, LocalDateTime startTime, LocalDateTime endTime)
	{
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
