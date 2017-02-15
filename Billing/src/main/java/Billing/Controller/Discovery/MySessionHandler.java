package Billing.Controller.Discovery;

import Billing.DTOs.AccountBroadcastDto;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import java.util.function.Function;


public class MySessionHandler extends StompSessionHandlerAdapter {
    public MySessionHandler(String send, String subscribe, Type type, DoFunction function) {
        this.send = send;//"/info/accountMsg"
        this.subscribe = subscribe;//"/data/accountBr"
        this.type=type;//AccountBroadcastDto.class;
        this.function=function;
    }

    String send,subscribe;
    Type type;
    DoFunction function;
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.send(send, new AccountBroadcastDto());
        session.subscribe(subscribe,  new StompFrameHandler() {


            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    function.handleObject(payload);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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