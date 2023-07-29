# discussion-hub
A Online Discussion Board(Forum) Using Web Socket and REST API

# Features
- Create, Edit, and Delete a Post
- Create, Edit, and Delete a Comment in a post real time
- Create, Edit, and Delete a Reply in a comment real time
- Receive Comment notification
- Receive Reply notification
- Comment Upvote
- Can Like in Post, Comment, and Reply
- Mention another user in Post, Comment, and Reply
- Comment and Reply READ and UNREAD flag for notification
  - Comments and Replies will only be mark as READ only when the author of the post or commentor of the comment open the associated modal
- Author can close the comment section for his/her every post
- User can block another user
  - The blocker and the blocked user will not see each other post, comment, and reply both by HTTPRequest and WebSocket
  - When the blocked user comment or reply in your posts or comments it will not be mark as READ
  - Notification for mention in post, comment, and reply
  - Notification for like in post, comment, and reply

# Access API endpoints quick and easy with POSTMAN
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/26932885-4e1fa1f7-9e7b-4089-aeca-68ab357fcde0?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D26932885-4e1fa1f7-9e7b-4089-aeca-68ab357fcde0%26entityType%3Dcollection%26workspaceId%3Dc37ab156-57a3-4304-8ee9-d7bdc45ae1f4)
