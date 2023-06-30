package zti.soccerlens.api;

import lombok.Value;
import zti.soccerlens.auth.api.ProfileResponse;

import java.time.LocalDateTime;

@Value
public class CommentResponse
{
	long commentId;
	long matchId;
	ProfileResponse user;
	String commentText;
	LocalDateTime timestamp;
}
