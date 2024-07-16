package com.elleined.socialmediaapi.model.notification.vote;

import com.elleined.socialmediaapi.model.notification.Notification;
import com.elleined.socialmediaapi.model.vote.Vote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "voteNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_vote_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VoteNotification extends Notification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "vote_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Vote vote;

    @Override
    public String getMessage() {
        return STR."\{this.getCreator().getName()} \{this.getVote().getVerdict().name()}d your comment: \"\{this.getVote().getComment().getBody()}\" ";
    }
}
