package com.elleined.socialmediaapi.dto.notification.vote;

import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import com.elleined.socialmediaapi.dto.vote.VoteDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VoteNotificationDTO extends NotificationDTO {
    private VoteDTO voteDTO;
}
