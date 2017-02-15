package Billing.DTOs;

import java.util.*;

/**
 * Created by Aismael on 14.02.2017.
 */
public class WebSocketConfigDto {
    public WebSocketConfigDto(String name, String out, String in,  ArrayList<WebSocketEndpointDto> endpointDtoMap) {
        this.name = name;
        this.out = out;
        this.in = in;
        this.endpointDtoMap = endpointDtoMap;
    }
    public WebSocketConfigDto(String name, String out, String in) {
        this.name = name;
        this.out = out;
        this.in = in;
        this.endpointDtoMap = null;
    }

    public WebSocketConfigDto() {
        this.endpointDtoMap = new ArrayList<>();;
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



    String name, out ,in;

    public ArrayList<WebSocketEndpointDto> getEndpointDtoMap() {
        return endpointDtoMap;
    }

    public void setEndpointDtoMap(ArrayList<WebSocketEndpointDto> endpointDtoMap) {
        this.endpointDtoMap = endpointDtoMap;
    }

    ArrayList<WebSocketEndpointDto> endpointDtoMap= new ArrayList<>();

    public String makeMapstring(){
        final String[] s = {""};
        endpointDtoMap.forEach((WebSocketEndpointDto e) -> {
            s[0] +=e.toString();
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
