# forum-api
An API that supports social media functionality.

# Configuration(Optional)
 - For my front end project im using Jquery and Ajax
 - To receive notification make sure to connect and subscribe to websocket URI
 - First connect in websocket endpoint
   ```
   const socket = new SockJS("http://localhost:8081/api/v1/forum/ws");
   const stompClient = Stomp.over(socket);
   stompClient.connect({}, onConnected,
   () => console.log("Could not connect to WebSocket server. Please refresh this page to try again!"));
   ```
## For real-time communications of forum subscribe to this topics
  - for comments
  ```
  const commentsTopic = `/discussion/posts/${postId}/comments`;
  let commentSubscription = stompClient.subscribe(commentsTopic, function (commentDto) {
      const json = JSON.parse(commentDto.body);
      // Execute code here
  });
  ```
  - for replies
  ```
  const repliesTopic = `/discussion/posts/comments/${commentId}/replies`;
  let replySubscription = stompClient.subscribe(repliesTopic, function (replyDto) {
      const json = JSON.parse(replyDto.body);
      // Execute code here
  });
  ```
## For real time notification subscribe to this topics 
   - for comment notification
     ```
       const commentNotificationTopic = "/notification/comments/${yourId}";
     ```
   - for reply notification
     ```
        const replyNotificationTopic = "/notification/replies/${yourId}";
     ```
   - for like notification
     ```
        const likeNotificationTopic = "/notification/likes/${yourId}";
     ```
   - for mention notification
     ```
        const mentionNotificationTopic = "/notification/mentions/${yourId}";
     ```
   - sample code
     ```
     stompClient.subscribe(commentNotificationTopic, function (payloadToBeAutomaticallyReceive) {
        const json = JSON.parse(payloadToBeAutomaticallyReceive.body); 
        // Execute code here
        // dto/notification folder contains the notification payloads
        // Feel free to replication block of code to subscribe in all the notifications topic
     });
     ```
     
# Features
- Create, Edit, and Delete a Post with or wthout picture
- Create, Edit, and Delete a Comment with or wthout picture in a post real time
- Create, Edit, and Delete a Reply with or wthout picture in a comment real time
- Like in Post, Comment, and Reply
- Mention another user in Post, Comment, and Reply
- Create, Update, Delete Reaction in Post
- Create, Update, Delete Reaction in Comment
- Create, Update, Delete Reaction in Reply
- Author can close the comment section for his/her every post
- Comment upvote
- Pin comment and reply
- Block another user
- Save and Unsave post
- Share and Unshare post
- Send, Accept, and Cancel friend request
- Follow and Unfollow users
- Web Socket for real time communication in comments and replies
- Receive real-time notification for comment and reply
- Receive real-time notification for mention in post, comment, and reply
- Receive real-time notification for like in post, comment, and reply
- Receive real-time notification for send and accept friend request

# Technologies used
- Spring boot
- Spring mvc
- Spring websocket
- Spring data jpa
 - Softwares used
   - MySQL
   - Postman
   - IntelliJ

# Access API endpoints quick and easy with POSTMAN
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/26932885-4e1fa1f7-9e7b-4089-aeca-68ab357fcde0?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D26932885-4e1fa1f7-9e7b-4089-aeca-68ab357fcde0%26entityType%3Dcollection%26workspaceId%3Dc37ab156-57a3-4304-8ee9-d7bdc45ae1f4)
