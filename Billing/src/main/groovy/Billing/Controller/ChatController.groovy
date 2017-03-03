package Billing.Controller

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component

/**
 * Created by Aismael on 02.03.2017.
 */
@Component
class ChatController {
    @Autowired
    private DiscoveryClient discoveryClient
    URI getESLUri() {
        List<ServiceInstance> list = discoveryClient.getInstances("ESL")
        if (list != null && list.size() > 0 )  return list.get(0).getUri()
        else return null
         }



    def connect(){
        return new HTTPBuilder("http://"+getESLUri().getHost()+":3000")
    }
    def getFromESL(path, Map headers)  throws HttpResponseException{
      getFromESL(path,headers,"")
    }
    def getFromESL(path, Map headers,param)  throws HttpResponseException{
        def http = connect()
        http.setHeaders(headers)
        return http.get(path : path,query: param)
    }
    def getFromESL(path) {
        getFromESL(path,new HashMap())
    }
    def postToESL(body,path, Map headers,param)  throws HttpResponseException{
        def http = connect()
        http.setHeaders(headers)
        return http.post(path : path,body:body,query: param)
    }
    def postToESL(body,path, Map headers)  throws HttpResponseException{
        postToESL(body,path,headers,"")
    }
    def postToESL(body,path) {
        postToESL(body,path,new HashMap())
    }

    boolean sendMsg(mail, msg) {
        return machineMethod(mail,msg)
    }

    MyState eS, fS ,aS
    Map cT
    boolean machineMethod(mail, msg) {
        eS = new MyState(funct:{return false})
        fS = new MyState(funct:{return true})
        aS = null
        cT=["Content-type":"application/json"]

        aS= new MyState(funct:login(),data: null,msg: msg,mail: mail)
        while ((aS!=eS||aS!=fS)&&aS instanceof MyState){
            try {
                aS=aS.stateRun()
            }catch (HttpResponseException e){
                aS=eS
            }
        }
        return aS.stateRun()
    }

    class MyState {
        Closure funct
        def msg,mail
        def stateRun(){
            funct()
        }

    }

    MyState login()  throws HttpResponseException{
            def result=postToESL('{ "user": "admin", "password": "supersecret" }',"/api/v1/login",cT)
                if(result.data){
                    cT.putAll(["X-Auth-Token":result.data.authToken,"X-User-Id":result.data.userId])
                    new MyState(funct:doUserExist(),msg: aS.msg,mail: aS.mail)
                }else{
                    return eS
                }
    }
    MyState doUserExist() throws HttpResponseException{
        def result=getFromESL("/api/v1/users.info",cT,["username":aS.mail.split(/ @\d/)[0]])
        if(result.success){
            return eS
        }else{
            return fS
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
