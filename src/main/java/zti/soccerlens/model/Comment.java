package zti.soccerlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import zti.soccerlens.api.CommentResponse;
import zti.soccerlens.auth.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment
{
	@Id
	@GeneratedValue
	private Long commentId;
	@ManyToOne
	@JoinColumn(name = "match_id")
	private Match match;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String commentText;
	private LocalDateTime timestamp = LocalDateTime.now();

	protected Comment() {}

	public Comment(Match match, User user, String commentText, LocalDateTime timestamp)
	{
		this.match = match;
		this.user = user;
		this.commentText = commentText;
		this.timestamp = timestamp;
	}

	public CommentResponse mapToResponse()
	{
		return new CommentResponse(this.commentId, this.match.getMatchId(), this.user.mapToResponse(),
				this.commentText, this.timestamp);
	}
}
