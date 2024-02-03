package com.elleined.forumapi.model.mention;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_post_mention")
@NoArgsConstructor
public class PostMention extends Mention {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            nullable = false,
            updatable = false
    )
    private Post post;

    @Builder(builderMethodName = "postMentionBuilder")
    public PostMention(int id, LocalDateTime createdAt, User mentionedUser, NotificationStatus notificationStatus, User mentioningUser, Post post) {
        super(id, createdAt, mentionedUser, notificationStatus, mentioningUser);
        this.post = post;
    }

    @Override
    public String getMessage() {
        return this.getMentioningUser().getName() + " mentioned you in a post: " + "\"" + this.getPost().getBody() + "\"";
    }

    @Override
    public int getReceiverId() {
        return getMentionedUser().getId();
    }

    @Override
    public boolean isEntityActive() {
        return this.getPost().isActive();
    }
}
