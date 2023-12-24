package com.elleined.forumapi.model.react;


import com.elleined.forumapi.model.NotificationStatus;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.emoji.Emoji;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class React {

    @Id
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "postEmojiAutoIncrement"
    )
    @SequenceGenerator(
            allocationSize = 1,
            name = "postEmojiAutoIncrement",
            sequenceName = "postEmojiAutoIncrement"
    )
    @Column(
            name = "id",
            nullable = false,
            unique = true,
            updatable = false
    )
    private int id;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "respondent_id",
            referencedColumnName = "user_id",
            nullable = false,
            updatable = false
    )
    private User respondent;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "notification_status",
            nullable = false
    )
    private NotificationStatus notificationStatus; // of the post author

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "emoji_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Emoji emoji;

    public abstract String getMessage();
    public abstract int getReceiverId();
}
