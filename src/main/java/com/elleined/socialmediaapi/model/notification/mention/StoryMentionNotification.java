package com.elleined.socialmediaapi.model.notification.mention;

import com.elleined.socialmediaapi.model.story.Story;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "storyMentionNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_story_mention_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StoryMentionNotification extends MentionNotification {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "story_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private Story story;

    @Override
    public String getMessage() {
        return STR."\{this.getCreator().getName()} mentioned you and others in a story";
    }
}
