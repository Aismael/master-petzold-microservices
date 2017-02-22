package Billing.Controller.Discovery;
import org.springframework.messaging.simp.stomp.*;
import java.lang.reflect.Type;

public class DtoOutOfJsonSessionHandler extends StompSessionHandlerAdapter {
    public DtoOutOfJsonSessionHandler(String send, String subscribe, Type type, DoFunction subscribeFunction, DoFunction sendFunction) {
        this.send = send;//"/info/accountMsg"
        this.subscribe = subscribe;//"/data/accountBr"
        this.type=type;//AccountBroadcastDto.class;
        this.subscribeFunction=subscribeFunction;
        this.sendFunction=sendFunction;

    }

    String send,subscribe;
    Type type;
    DoFunction subscribeFunction,sendFunction;
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.send(send, sendFunction.handleObject(getPayloadType(connectedHeaders)));
        session.subscribe(subscribe,  new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    subscribeFunction.handleObject(payload);
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