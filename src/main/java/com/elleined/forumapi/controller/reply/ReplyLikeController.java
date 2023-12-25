package com.elleined.forumapi.controller.reply;

import com.elleined.forumapi.dto.ReplyDTO;
import com.elleined.forumapi.mapper.ReplyMapper;
import com.elleined.forumapi.model.Reply;
import com.elleined.forumapi.model.User;
import com.elleined.forumapi.model.like.ReplyLike;
import com.elleined.forumapi.service.ReplyService;
import com.elleined.forumapi.service.UserService;
import com.elleined.forumapi.service.ws.notification.like.ReplyWSLikeNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/{currentUserId}/posts/comments/{commentId}/replies")
public class ReplyLikeController {
    private final UserService userService;

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    private final ReplyWSLikeNotificationService replyLikeWSNotificationService;

    @PatchMapping("/like/{replyId}")
    public ReplyDTO like(@PathVariable("currentUserId") int respondentId,
                         @PathVariable("replyId") int replyId) {

        User respondent = userService.getById(respondentId);
        Reply reply = replyService.getById(replyId);

        if (replyService.isLiked(respondent, reply)) {
            replyService.unLike(respondent, reply);
            return replyMapper.toDTO(reply);
        }

        ReplyLike replyLike = replyService.like(respondent, reply);
        replyLikeWSNotificationService.broadcast(replyLike);
        return replyMapper.toDTO(reply);
    }
}
