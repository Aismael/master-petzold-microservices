package Billing.Loader

import Billing.DTOs.WebSocketEndpointDto
import Billing.DTOs.WebSocketConfigDto
import groovy.json.JsonSlurper

/**
 * Created by Aismael on 14.02.2017.
 */


def card = new JsonSlurper().parse(new URL("http://192.168.99.100:8080/config/json/"))
def broadCastWebsocketData =card.config.broadcast
WebSocketConfigDto websocketConfigDto= new WebSocketConfigDto()
websocketConfigDto.name=broadCastWebsocketData.name
websocketConfigDto.in=broadCastWebsocketData.in
websocketConfigDto.in=broadCastWebsocketData.out
broadCastWebsocketData.endpoint.each {
    println it
    def key=it.getKey()
    WebSocketEndpointDto webSocketEndpointDto= new WebSocketEndpointDto()
    webSocketEndpointDto.name="$key"
    webSocketEndpointDto.message=it.properties.value.message
    webSocketEndpointDto.sendPath=it.properties.value.sendPath
    websocketConfigDto.endpointDtoMap.push(webSocketEndpointDto)
    //println webSocketEndpointDto

}

println websocketConfigDto.endpointDtoMap

