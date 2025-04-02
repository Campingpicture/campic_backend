package team.campic.sns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.campic.sns.dto.LikeResponse;
import team.campic.sns.entity.Like;
import team.campic.sns.entity.Post;
import team.campic.sns.repository.LikeRepository;
import team.campic.sns.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    private LikeResponse mapToDto(Like like) {
        LikeResponse dto = new LikeResponse();
        dto.setId(like.getId());
        return dto;
    }

    public List<LikeResponse> getLikesByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found");
        }
        return likeRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public LikeResponse addLike(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Like like = new Like();
        like.setPost(post);
        Like savedLike = likeRepository.save(like);
        return mapToDto(savedLike);
    }

    public void removeLike(Long postId, Long likeId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found");
        }
        Like like = likeRepository.findByPostId(postId)
                .stream()
                .filter(l -> l.getId().equals(likeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);
    }
}