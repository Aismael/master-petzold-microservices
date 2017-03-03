package Billing.Loader

import com.google.common.base.Charsets
import groovyx.net.http.EncoderRegistry
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
import org.apache.commons.codec.net.URLCodec


testx =new Test()
println ("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
println(testx.sendMsg("admin@example.com","Hi"))
println ("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")
println(testx.sendMsg("admin@error.com","Hi"))
println ("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww")


class Test {

    HTTPBuilder http=null
    def connectToESL() {
        def timeOut = 10000
        HTTPBuilder http= new HTTPBuilder('http://192.168.99.100:3000')
        http.client.params.setParameter('http.connection.timeout', new Integer(timeOut))
        http.client.params.setParameter('http.socket.timeout', new Integer(timeOut))
        return http
    }

    def getFromESL(path, Map headers) throws HttpResponseException {
        return getFromESL(path, headers, new HashMap())
    }

    def getFromESL(path, Map headers, Map param) throws HttpResponseException {
        if(!http) http = connectToESL()
        http.setHeaders(headers)
        return http.get(path: path, query: param)
    }

    def getFromESL(path) {
        return getFromESL(path, new HashMap())
    }

    def postToESL(body, path, Map headers, Map param) throws HttpResponseException {
        if(!http) http = connectToESL()
        http.setHeaders(headers)
        return http.post(path: path, body: body, query: param)
    }

    def postToESL(body, path, Map headers) throws HttpResponseException {
        return postToESL(body, path, headers, new HashMap())
    }

    def postToESL(body, path) {
        return postToESL(body, path, new HashMap())
    }

    boolean sendMsg(mail, msg) {
        return machineMethod(mail, msg)
    }

    MyState eS, fS, aS
    Map cT

    boolean machineMethod(String mail, msg) {
        eS = new MyState(funct: { http=null;return false })
        fS = new MyState(funct: { http=null;return true })
        aS = null
        cT = ["Content-type": "application/json"]
        aS = new MyState(funct: {return login()}, data: null, msg: msg, mail: mail,name: mail.replace("@","."))
        while (true) {
            try {
                aS = aS.stateRun()
            } catch (HttpResponseException e) {
                aS = eS
                break
            }
            println aS
            if(aS == eS || aS == fS || !(aS instanceof MyState))
            break
        }
        return aS.stateRun()
    }

    class MyState {
        Closure funct
        def msg,data, mail, name

        def stateRun() throws HttpResponseException{
            funct()
        }
    }

    MyState login() throws HttpResponseException {
        def result = postToESL('{ "user": "admin", "password": "supersecret" }', "/api/v1/login", cT)
        if (result.status=="success") {
            cT.putAll(["X-Auth-Token": result.data.authToken, "X-User-Id": result.data.userId])
            return new MyState(funct: {return doUserExist()}, msg: aS.msg, mail: aS.mail,name: aS.name)
        } else {
            return eS
        }
    }

    MyState doUserExist() throws HttpResponseException {
        def result = getFromESL("/api/v1/users.info", cT, ["username": aS.name])
        if (result.success=="true") {
            return new MyState(funct: {return doChannelExists()}, msg: aS.msg, mail: aS.mail,name: aS.name)
        } else {
            return new MyState(funct: {return makeUser()}, msg: aS.msg, mail: aS.mail,name: aS.name)

        }

    }

    MyState makeUser() {
        return false
    }

    MyState doChannelExists() {
        return false
    }

    MyState makeChannel() {
        return false
    }
}

