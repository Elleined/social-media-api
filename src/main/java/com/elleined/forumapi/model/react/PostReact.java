package com.elleined.forumapi.model.react;

import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.Post;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_post_emoji")
@NoArgsConstructor
public class PostReact extends React {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "post_id",
            nullable = false,
            updatable = false
    )
    private Post post;

    @Builder(builderMethodName = "postEmojiBuilder")
    public PostReact(int id, LocalDateTime createdAt, User respondent, NotificationStatus notificationStatus, Emoji emoji, Post post) {
        super(id, createdAt, respondent, notificationStatus, emoji);
        this.post = post;
    }

    @Override
    public String getMessage() {
        return this.getRespondent().getName() + " reacted " + getEmoji().getType().name() + " to your post " + post.getBody();
    }

    @Override
    public int getReceiverId() {
        return post.getAuthor().getId();
    }
}
