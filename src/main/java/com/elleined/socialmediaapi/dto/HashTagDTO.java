package com.elleined.socialmediaapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HashTagDTO {
    private int id;
    private String keyword;
//    private Set<Integer> connectedPostIds;
//    private Set<Integer> connectedComments;
//    private Set<Integer> connectedReplies;
}
