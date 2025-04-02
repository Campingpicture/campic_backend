package team.campic.sns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.campic.sns.dto.LikeResponse;
import team.campic.sns.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("")
    public ResponseEntity<List<LikeResponse>> getLikesByPost(@PathVariable Long postId) {
        try {
            List<LikeResponse> responses = likeService.getLikesByPostId(postId);
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<LikeResponse> addLike(@PathVariable Long postId) {
        try {
            LikeResponse response = likeService.addLike(postId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> removeLike(@PathVariable Long postId, @PathVariable Long likeId) {
        try {
            likeService.removeLike(postId, likeId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
