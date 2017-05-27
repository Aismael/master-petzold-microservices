package Billing.DTOs;

/**
 * DTO eines Websocket Endpunktes
 * Created by Martin Petzold on 14.02.2017.
 */
public class WebSocketEndpointDto {
    String message;
    String sendPath;
    String name;

    public WebSocketEndpointDto() {

    }

    /**
     * @param message  Pfad für die ankommenden Nachrichten
     * @param sendPath Pfad für die Ausgehenden Nachrichten
     * @param name     Bezeichnung/Identifier des Endpunktes
     */
    public WebSocketEndpointDto(String message, String sendPath, String name) {
        this.message = message;
        this.sendPath = sendPath;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebSocketEndpointDto{" +
                "message='" + message + '\'' +
                ", sendPath='" + sendPath + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
