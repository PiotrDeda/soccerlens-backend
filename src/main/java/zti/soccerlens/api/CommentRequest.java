package zti.soccerlens.api;

import lombok.Value;

@Value
public class CommentRequest
{
	long matchId;
	String commentText;
}
