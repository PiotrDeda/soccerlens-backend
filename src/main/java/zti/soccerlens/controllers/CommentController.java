package zti.soccerlens.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import zti.soccerlens.api.CommentRequest;
import zti.soccerlens.api.CommentResponse;
import zti.soccerlens.auth.User;
import zti.soccerlens.auth.UserDetailsImpl;
import zti.soccerlens.auth.UserRepository;
import zti.soccerlens.model.Comment;
import zti.soccerlens.model.Match;
import zti.soccerlens.repositories.CommentRepository;
import zti.soccerlens.repositories.MatchRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController
{
	private final CommentRepository commentRepository;
	private final MatchRepository matchRepository;
	private final UserRepository userRepository;

	public CommentController(CommentRepository commentRepository, MatchRepository matchRepository,
							 UserRepository userRepository)
	{
		this.commentRepository = commentRepository;
		this.matchRepository = matchRepository;
		this.userRepository = userRepository;
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<CommentResponse> getAllComments()
	{
		return commentRepository.findAll().stream().map(Comment::mapToResponse).toList();
	}

	@GetMapping("/{id}")
	public CommentResponse getCommentById(@PathVariable Long id)
	{
		return commentRepository.findById(id).map(Comment::mapToResponse).orElse(null);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public CommentResponse addComment(@RequestBody CommentRequest comment)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Match match = matchRepository.findById(comment.getMatchId()).orElse(null);
		if (match == null)
			return null;
		User user = userRepository.findById(userDetails.getUserId()).orElseThrow();
		return commentRepository.save(new Comment(match, user, comment.getCommentText(), LocalDateTime.now()))
				.mapToResponse();
	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateComment(@RequestBody CommentRequest comment)
	{
		return new ResponseEntity<>("Not implemented", HttpStatus.NOT_IMPLEMENTED);
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		if (!Objects.equals(comment.getUser().getUserId(), userDetails.getUserId()))
			return null;
		return commentRepository.save(comment).mapToResponse();*/
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public void deleteComment(@PathVariable Long id)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Comment comment = commentRepository.findById(id).orElse(null);
		if (comment != null && !Objects.equals(comment.getUser().getUserId(), userDetails.getUserId()) &&
				authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
			return;
		commentRepository.deleteById(id);
	}
}
