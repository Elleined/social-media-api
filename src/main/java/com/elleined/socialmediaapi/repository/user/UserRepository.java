package com.elleined.socialmediaapi.repository.user;

import com.elleined.socialmediaapi.model.friend.FriendRequest;
import com.elleined.socialmediaapi.model.main.post.Post;
import com.elleined.socialmediaapi.model.notification.main.CommentNotification;
import com.elleined.socialmediaapi.model.notification.main.ReplyNotification;
import com.elleined.socialmediaapi.model.notification.mention.CommentMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.PostMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.ReplyMentionNotification;
import com.elleined.socialmediaapi.model.notification.mention.StoryMentionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.CommentReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.PostReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.ReplyReactionNotification;
import com.elleined.socialmediaapi.model.notification.reaction.StoryReactionNotification;
import com.elleined.socialmediaapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.name LIKE CONCAT('%', :name, '%')")
    Page<User> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT u.blockedUsers FROM User u WHERE u = :currentUser")
    Page<User> findAllBlockedUsers(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.followers FROM User u WHERE u = :currentUser")
    Page<User> findAllFollowers(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.followings FROM User u WHERE u = :currentUser")
    Page<User> findAllFollowings(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.friends FROM User u WHERE u = :currentUser")
    Page<User> findAllFriends(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.receiveFriendRequests FROM User u WHERE u = :currentUser")
    Page<FriendRequest> findAllFriendRequests(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.savedPosts FROM User u WHERE u = :currentUser")
    Page<Post> findAllSavedPosts(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.sharedPosts FROM User u WHERE u = :currentUser")
    Page<Post> findAllSharedPosts(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.commentNotifications FROM User u WHERE u = :currentUser")
    Page<CommentNotification> findAllReceiveCommentNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.replyNotifications FROM User u WHERE u = :currentUser")
    Page<ReplyNotification> findAllReceiveReplyNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.postMentionNotifications FROM User u WHERE u = :currentUser")
    Page<PostMentionNotification> findAllPostMentionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.commentMentionNotifications FROM User u WHERE u = :currentUser")
    Page<CommentMentionNotification> findAllCommentMentionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.replyMentionNotifications FROM User u WHERE u = :currentUser")
    Page<ReplyMentionNotification> findAllReplyMentionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.storyMentionNotifications FROM User u WHERE u = :currentUser")
    Page<StoryMentionNotification> findAllStoryMentionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.postReactionNotifications FROM User u WHERE u = :currentUser")
    Page<PostReactionNotification> findAllPostReactionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.commentReactionNotifications FROM User u WHERE u = :currentUser")
    Page<CommentReactionNotification> findAllCommentReactionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.replyReactionNotifications FROM User u WHERE u = :currentUser")
    Page<ReplyReactionNotification> findAllReplyReactionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

    @Query("SELECT u.storyReactionNotifications FROM User u WHERE u = :currentUser")
    Page<StoryReactionNotification> findAllStoryReactionNotifications(@Param("currentUser") User currentUser, Pageable pageable);

}