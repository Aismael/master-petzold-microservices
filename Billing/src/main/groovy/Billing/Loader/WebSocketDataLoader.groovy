package Billing.Loader

import Billing.DTOs.WebSocketConfigDto
import Billing.DTOs.WebSocketEndpointDto
import groovy.json.JsonException
import groovy.json.JsonSlurper
import org.springframework.stereotype.Component

/**
 * Lädt die Daten der beim Order Service vorhandenen Websockets
 * aus der Rest Configuration
 * Created by Aismael on 15.02.2017.
 */
@Component
class WebSocketDataLoader {
    /**
     * Laden unParsen der Websocketdaten von der RestConfiguration
     * @param url des Anderen Services
     * @return Ein DTO der Websocketconfiguration
     * @throws Exception
     * @throws JsonException
     */
    WebSocketConfigDto getFromJSONUrL(URL url ) throws Exception, JsonException{
        def input=null
        try {
            input = new JsonSlurper().parse(url)
        }catch (Exception e){

        }
        if(input) {
            def broadCastWebSocketData = input.config.broadcast

            WebSocketConfigDto websocketConfigDto = new WebSocketConfigDto()

            websocketConfigDto.name = broadCastWebSocketData.name
            websocketConfigDto.in = broadCastWebSocketData.in
            websocketConfigDto.out = broadCastWebSocketData.out

            broadCastWebSocketData.endpoint.each {
                println it
                def key = it.getKey()
                WebSocketEndpointDto webSocketEndpointDto = new WebSocketEndpointDto()
                webSocketEndpointDto.name = "$key"
                webSocketEndpointDto.message = it.properties.value.message
                webSocketEndpointDto.sendPath = it.properties.value.sendPath
                websocketConfigDto.endpointDtoMap.push(webSocketEndpointDto)
            }

            println websocketConfigDto.endpointDtoMap
            return websocketConfigDto
        }
    }
}
