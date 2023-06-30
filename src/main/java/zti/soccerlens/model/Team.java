package zti.soccerlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Team
{
	@Id
	@GeneratedValue
	private Long teamId;
	private String teamName;

	protected Team() {}

	public Team(String teamName)
	{
		this.teamName = teamName;
	}
}
