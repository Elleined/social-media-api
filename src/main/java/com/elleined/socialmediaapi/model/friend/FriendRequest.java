package com.elleined.socialmediaapi.model.friend;

import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import com.elleined.socialmediaapi.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tbl_friend_request")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FriendRequest extends PrimaryKeyIdentity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User creator;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "requested_user_id",
            referencedColumnName = "id",
            nullable = false,
            updatable = false
    )
    private User requestedUser;
}
