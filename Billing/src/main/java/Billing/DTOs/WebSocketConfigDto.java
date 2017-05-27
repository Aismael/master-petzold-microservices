package Billing.DTOs;

import java.util.ArrayList;

/**
 * DTO um die Eingelesenen Daten des Order Websockets zu parsen
 * Created by Martin Petzold on 14.02.2017.
 */
public class WebSocketConfigDto {
    String name, out, in;
    ArrayList<WebSocketEndpointDto> endpointDtoMap = new ArrayList<>();

    /**
     * Konstruktor mit allen Daten
     *
     * @param name           name/pfad des Websockets
     * @param out            pfad der vom Websocket ausgehenden Nachrichten
     * @param in             pfad der um Nachrichten an den Websocket zu senden
     * @param endpointDtoMap Endpunkte des Websockets für die verschiedenen Nachrichten
     */
    public WebSocketConfigDto(String name, String out, String in, ArrayList<WebSocketEndpointDto> endpointDtoMap) {
        this.name = name;
        this.out = out;
        this.in = in;
        this.endpointDtoMap = endpointDtoMap;
    }

    /**
     * Konstruktor für einen Websocket ohne Endpunkte z.b. um die allgemeine Erreichbarkeit des Services zu testen
     *
     * @param name name/pfad des Websockets
     * @param out  pfad der vom Websocket ausgehenden Nachrichten
     * @param in   pfad der um Nachrichten an den Websocket zu senden
     */
    public WebSocketConfigDto(String name, String out, String in) {
        this.name = name;
        this.out = out;
        this.in = in;
        this.endpointDtoMap = null;
    }

    public WebSocketConfigDto() {
        this.endpointDtoMap = new ArrayList<>();
        ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public ArrayList<WebSocketEndpointDto> getEndpointDtoMap() {
        return endpointDtoMap;
    }

    public void setEndpointDtoMap(ArrayList<WebSocketEndpointDto> endpointDtoMap) {
        this.endpointDtoMap = endpointDtoMap;
    }

    public String makeMapstring() {
        final String[] s = {""};
        endpointDtoMap.forEach((WebSocketEndpointDto e) -> {
            s[0] += e.toString();
        });
        return s[0];
    }

    @Override
    public String toString() {
        return "WebSocketConfigDto{" +
                "name='" + name + '\'' +
                ", out='" + out + '\'' +
                ", in='" + in + '\'' +
                ", endpointDtoMap=" + makeMapstring() +
                '}';
    }
}
