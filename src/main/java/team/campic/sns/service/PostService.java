package team.campic.sns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.campic.sns.dto.CommentResponse;
import team.campic.sns.dto.LikeResponse;
import team.campic.sns.dto.PostRequest;
import team.campic.sns.dto.PostResponse;
import team.campic.sns.entity.Post;
import team.campic.sns.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 엔티티를 DTO로 변환
    private PostResponse mapToDto(Post post) {
        PostResponse dto = new PostResponse();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setComments(post.getComments().stream().map(comment -> {
            CommentResponse cr = new CommentResponse();
            cr.setId(comment.getId());
            cr.setText(comment.getText());
            return cr;
        }).collect(Collectors.toList()));
        dto.setLikes(post.getLikes().stream().map(like -> {
            LikeResponse lr = new LikeResponse();
            lr.setId(like.getId());
            return lr;
        }).collect(Collectors.toList()));
        return dto;
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToDto(post);
    }

    public PostResponse createPost(PostRequest request) {
        Post post = new Post();
        post.setContent(request.getContent());
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(request.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }
}