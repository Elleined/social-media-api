package com.elleined.forumapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class HashTagDTO {
    private int id;
    private String keyword;
    private LocalDateTime createdAt;
    private Set<Integer> connectedPosts;
//    private Set<Integer> connectedComments;
//    private Set<Integer> connectedReplies;
}
