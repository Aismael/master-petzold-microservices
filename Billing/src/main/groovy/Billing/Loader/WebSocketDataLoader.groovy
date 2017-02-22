package Billing.Loader

import Billing.DTOs.WebSocketConfigDto
import Billing.DTOs.WebSocketEndpointDto
import groovy.json.JsonSlurper
import org.springframework.stereotype.Component

/**
 * Created by Aismael on 15.02.2017.
 */
@Component
class WebSocketDataLoader {
    WebSocketConfigDto getFromJSONUrL(URL url ) {
        def input = new JsonSlurper().parse(url)
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
