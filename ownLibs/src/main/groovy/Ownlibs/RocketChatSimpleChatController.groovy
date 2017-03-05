package Ownlibs

import groovy.json.JsonBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component

/**
 * Created by Aismael on 02.03.2017.
 */
@Component
class RocketChatSimpleChatController {
    @Autowired
    private DiscoveryClient discoveryClient
    HTTPBuilder http = null
    MyState eS, fS, aS
    Map cT


    URI getESLUri() {
        List<ServiceInstance> list = discoveryClient.getInstances("ESL")
        if (list != null && list.size() > 0) return list.get(0).getUri()
        else return new URI("192.168.99.100")
    }

    def connectToESL() {
        def timeOut = 10000
        HTTPBuilder http = new HTTPBuilder("http://" + getESLUri().getHost() + ":3000")
        http.client.params.setParameter('http.connection.timeout', new Integer(timeOut))
        http.client.params.setParameter('http.socket.timeout', new Integer(timeOut))
        return http
    }

    def getFromESL(path, Map headers) {
        return getFromESL(path, headers, new HashMap())
    }

    def getFromESL(path, Map headers, Map param) {
        if (!http) http = connectToESL()
        http.setHeaders(headers)
        try {
            return http.get(path: path, query: param) { HttpResponseDecorator resp, reader ->
                return reader
            }
        } catch (HttpResponseException e) {
            return ["success": e.getMessage()]
        }
    }

    def getFromESL(path) {
        return getFromESL(path, new HashMap())
    }

    def postToESL(body, path, Map headers, Map param) {
        if (!http) http = connectToESL()
        http.setHeaders(headers)
        try {
            http.post(path: path, body: body, query: param) { HttpResponseDecorator resp, reader ->
                return reader
            }
        } catch (HttpResponseException e) {
            return ["success": e.getMessage()]
        }
    }

    def postToESL(body, path, Map headers) {
        return postToESL(body, path, headers, new HashMap())
    }

    def postToESL(body, path) {
        return postToESL(body, path, new HashMap())
    }

    boolean sendMsg(mail, msg) {
        return machineMethod(mail, msg)
    }

    boolean machineMethod(String mail, msg) {
        eS = new MyState(funct: { http = null; return false })
        fS = new MyState(funct: { http = null; return true })
        aS = null
        cT = ["Content-type": "application/json"]
        aS = new MyState(funct: { return login() }, data: null, msg: msg, mail: mail, name: mail.replace("@", "."))
        while (true) {
            try {
                aS = aS.stateRun()
            } catch (Exception e) {
                e.printStackTrace()
                aS = eS
                break
            }
            if (aS == eS || aS == fS || !(aS instanceof MyState))
                break
        }
        return aS.stateRun()
    }

    class MyState {
        Closure funct
        def msg, data, mail, name
        def stateRun() {
            funct()
        }
    }

    MyState login() {
        def json = new JsonBuilder()
        def root = json {
            user "admin.example.com"
            password "supersecret"
        }
        def result = postToESL(json.toString(), "/api/v1/login", cT)
        if (result.status == "success") {
            cT.putAll(["X-Auth-Token": result.data.authToken, "X-User-Id": result.data.userId])
            return new MyState(funct: { return doUserExist() }, msg: aS.msg, mail: aS.mail, name: aS.name)
        } else {
            return eS
        }
    }

    MyState doUserExist() {
        def result = getFromESL("/api/v1/users.info", cT, ["username": aS.name])
        if (result.success == true) {
            return new MyState(funct: { return doChannelExists() }, msg: aS.msg, mail: aS.mail, name: aS.name)
        } else {
            return new MyState(funct: { return makeUser() }, msg: aS.msg, mail: aS.mail, name: aS.name)

        }

    }

    MyState makeUser() {
        def json = new JsonBuilder()
        def root = json {
            name aS.name
            email aS.mail
            password aS.name
            username aS.name
        }
        def result = postToESL(json.toString(), "/api/v1/users.create", cT)
        if (result.success == true) {
            return new MyState(funct: { return doChannelExists() }, msg: aS.msg, mail: aS.mail, name: aS.name)
        } else {
            return eS

        }
    }

    MyState doChannelExists() {
        def result = getFromESL("/api/v1/channels.list", cT)
        def cexits = false
        result.channels.each {
            if (it.name == aS.name) {
                cexits = true
            }
        }
        if (result.success == true && cexits) {
            return new MyState(funct: { return sendMsgToServer() }, msg: aS.msg, mail: aS.mail, name: aS.name)
        } else {
            return new MyState(funct: { return makeChannel() }, msg: aS.msg, mail: aS.mail, name: aS.name)

        }
    }

    MyState sendMsgToServer() {
        def json = new JsonBuilder()
        def root = json {
            channel "#" + aS.name.toString()
            text aS.msg
        }
        def result = postToESL(json.toString(), "/api/v1/chat.postMessage", cT)
        if (result.success == true) {
            return fS
        } else {
            return eS

        }
    }

    MyState makeChannel() {
        def json = new JsonBuilder()
        def root = json {
            name aS.name
            members "admin.example.com", aS.name
        }
        def result = postToESL(json.toString(), "/api/v1/channels.create", cT)
        if (result.success == true) {
            return new MyState(funct: { return sendMsgToServer() }, msg: aS.msg, mail: aS.mail, name: aS.name)
        } else {
            return eS

        }
    }

}
