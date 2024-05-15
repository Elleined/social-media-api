package com.elleined.socialmediaapi.mapper.notification;

import com.elleined.socialmediaapi.dto.notification.NotificationDTO;
import com.elleined.socialmediaapi.mapper.CustomMapper;
import com.elleined.socialmediaapi.model.PrimaryKeyIdentity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotificationMapper<T extends PrimaryKeyIdentity> extends CustomMapper<T, NotificationDTO>  {
//
//    @Override
//    @Mappings({
//        @Mapping(target = "", source = ""),
//            @Mapping(target = "", source = ""),
//            @Mapping(target = "", source = ""),
//            @Mapping(target = "", source = ""),
//            @Mapping(target = "", source = ""),
//            int id,
//            LocalDateTime createdAt,
//            LocalDateTime updatedAt,
//            int creatorId,
//            int receiverId,
//            String message,
//            String notificationStatus
//    })
//    NotificationDTO toDTO(T t);
}
