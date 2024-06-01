package com.elleined.socialmediaapi.model.notification.mention;

import com.elleined.socialmediaapi.model.main.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_post_mention_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PostMentionNotification extends MentionNotification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Post post;

    @Override
    public String getMessage() {
        return STR."\{this.getCreator().getName()} mentioned you and others in a post: \"\{this.getPost().getBody()}\" ";
    }
}
