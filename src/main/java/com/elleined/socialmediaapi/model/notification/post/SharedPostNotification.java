package com.elleined.socialmediaapi.model.notification.post;

import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_post_shared_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SharedPostNotification extends Notification {

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
        return STR."\{this.getCreator().getName()} shared your post: \"\{this.getPost().getBody()}\" ";
    }
}
