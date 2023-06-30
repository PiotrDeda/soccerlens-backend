package zti.soccerlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MatchEvent
{
	@Id
	@GeneratedValue
	private Long matchEventId;
	@ManyToOne
	@JoinColumn(name = "match_id")
	private Match match;
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	@ManyToOne
	@JoinColumn(name = "second_player_id")
	private Player secondPlayer;
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	private Integer minute;

	protected MatchEvent() {}

	public MatchEvent(Match match, Player player, EventType eventType, Integer minute)
	{
		this.match = match;
		this.player = player;
		this.eventType = eventType;
		this.minute = minute;
	}

	public MatchEvent(Match match, Player player, Player secondPlayer, EventType eventType, Integer minute)
	{
		this.match = match;
		this.player = player;
		this.secondPlayer = secondPlayer;
		this.eventType = eventType;
		this.minute = minute;
	}
}
