package Billing.Controller;

import com.google.gson.JsonObject;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class MySessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.send("/info/accountIn", "payload");
        session.subscribe("/data/accountOut",  new StompFrameHandler() {


            @Override
            public Type getPayloadType(StompHeaders headers) {
                return HashMap.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println(payload);
            }

        });

        System.out.println("New session: {}"+session.getSessionId());
    }


    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.err.println("error");
        exception.printStackTrace();
    }


}