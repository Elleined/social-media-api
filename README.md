# social-media-api
REST API that supports social media functionalities
     
# Features
- Create, read, update, and delete post with or without picture
- Create, read, update, and delete comment with or without picture in a post real time
- Create, read, update, and delete reply with or without picture in a comment real time
- Mention another user in post, comment, and reply
- Create, read, update, and delete reaction in post, comment, and reply
- Author can close the comment section for his/her every post
- Create HashTag in post and Link hashtag in comment and reply
- Comment upvote
- Pin comment and reply
- Block another user
- Save, un-save, and get all un-save post
- Share, un-share, and get all un-share post
- Create, read, update, and delete note
- Create, read, update, and elete story
- Send, accept, and reject friend request
- Follow and Unfollow users
 
# Run using Docker
## 1. Create network
```
docker network sma-network
```

## 2. Docker Run MySQL Server
```
docker run -itd --rm -p 3307:3306 --network sma-network --name sma_mysql_server -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=social_media_api_db mysql:8.0.32
```

## 3. Docker Run Social Media API
```
docker run -itd --rm -p 8081:8081 --network sma-network --name sma_app sma
```

# Access API endpoints quick and easy with POSTMAN
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/26932885-4e1fa1f7-9e7b-4089-aeca-68ab357fcde0?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D26932885-4e1fa1f7-9e7b-4089-aeca-68ab357fcde0%26entityType%3Dcollection%26workspaceId%3Dc37ab156-57a3-4304-8ee9-d7bdc45ae1f4)
