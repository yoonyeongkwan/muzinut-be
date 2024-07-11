package nuts.muzinut.config;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.handler.ChatPreHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatPreHandler chatPreHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/send");    //클라이언트에서 보낸 메세지를 받을 prefix (메세지 구독 요청 url)
        registry.enableSimpleBroker("/room"); //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달 (메세지 발행 요청 url)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") //SocketJs 연결 주소
//                .setAllowedOrigins("*");
                .setAllowedOriginPatterns("*");
//                .withSockJS(); //Todo 프론트와 채팅 통신할 때 주석 해제
        // 주소 : ws://localhost:8080/ws
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(chatPreHandler);
    }
}
