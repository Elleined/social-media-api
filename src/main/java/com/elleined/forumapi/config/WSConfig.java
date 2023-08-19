package com.elleined.forumapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WSConfig implements WebSocketMessageBrokerConfigurer {

    private final UserHandshakeHandler userHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                // .setHandshakeHandler(userHandshakeHandler)
                .setAllowedOriginPatterns("*") // Used to allow other ports to connect in this websocket
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
            registry.enableSimpleBroker("/forum", "/notification");
            registry.setApplicationDestinationPrefixes("/app");
    }
}
