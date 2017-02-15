package Billing.DTOs;

/**
 * Created by Aismael on 14.02.2017.
 */
public class WebSocketEndpointDto {
    String message;
    String sendPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public WebSocketEndpointDto() {

    }

    public WebSocketEndpointDto(String message, String sendPath, String name) {
        this.message = message;
        this.sendPath = sendPath;
        this.name = name;

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
