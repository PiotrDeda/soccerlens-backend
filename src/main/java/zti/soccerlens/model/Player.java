package zti.soccerlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player
{
	@Id
	@GeneratedValue
	private Long playerId;
	private String playerName;
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	protected Player() {}

	public Player(Team team, String playerName)
	{
		this.team = team;
		this.playerName = playerName;
	}
}
