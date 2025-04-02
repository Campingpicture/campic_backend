package team.campic.sns.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private String content;
    private List<CommentResponse> comments;
    private List<LikeResponse> likes;
}