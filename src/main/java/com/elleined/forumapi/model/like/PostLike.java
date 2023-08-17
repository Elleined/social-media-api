package com.elleined.forumapi.model.like;

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

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_liked_post")
@NoArgsConstructor
public final class PostLike extends Like {

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id"
    )
    @Getter
    private Post post;

    @Builder(builderMethodName = "postLikeBuilder")
    public PostLike(int id, User respondent, LocalDateTime createdAt, NotificationStatus notificationStatus, Post post) {
        super(id, respondent, createdAt, notificationStatus);
        this.post = post;
    }


    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " liked your post: " +  "\"" + this.getPost().getBody() + "\"";
    }

    @Override
    public int getReceiverId() {
        return post.getAuthor().getId();
    }

}
