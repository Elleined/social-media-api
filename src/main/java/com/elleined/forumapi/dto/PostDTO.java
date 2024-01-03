package com.elleined.forumapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class PostDTO {
    private int id;
    private String body;
    private LocalDateTime dateCreated;
    private String formattedDateCreated;
    private String formattedTimeCreated;
    private int authorId;
    private String authorName;
    private String authorPicture;
    private int totalCommentAndReplies;
    private String status;
    private String commentSectionStatus;
    private String attachedPicture;
    private Set<UserDTO> mentionedUsers;
    private Set<UserDTO> savingUsers;
    private Set<UserDTO> sharers;
    private int pinnedCommentId;
}
