package com.elleined.socialmediaapi.model.notification.reaction;

import com.elleined.socialmediaapi.model.reaction.Emoji;
import com.elleined.socialmediaapi.model.story.Story;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@org.hibernate.annotations.Cache(region = "storyReactionNotificationCache", usage = CacheConcurrencyStrategy.READ_WRITE)

@Entity
@Table(name = "tbl_story_reaction_notification")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StoryReactionNotification extends ReactionNotification {

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
        if (this.getReaction().getEmoji().getType().equals(Emoji.Type.HEART.name()))
            return STR."\{this.getCreator().getName()} loves to your story";

        return STR."\{this.getCreator().getName()} reacted to your story";
    }
}
