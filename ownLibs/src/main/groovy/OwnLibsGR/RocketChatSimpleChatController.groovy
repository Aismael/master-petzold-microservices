package OwnLibsGR

import groovy.json.JsonBuilder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component

/**
 * Der Kontroller um an Rocketchat Nachrichten zu senden,
 * und bei bedarf Channels und User zu erstellen
 * dabei wird Eine simple Statemachine um eine Nachricht zu senden und
 * Vorbereitungen für das senden zu treffen
 * Created by Martin Petzold on 02.03.2017.
 */
@Component
class RocketChatSimpleChatController {
    @Autowired
    private DiscoveryClient discoveryClient
    HTTPBuilder http = null
    MyState eS, fS, aS
    Map cT

    /**
     * sucht die URI des Rocketchat Services
     * mithilfe von Eureka
     * @return URI des Rocketchat Services
     */
    URI getESLUri() {
        List<ServiceInstance> list = discoveryClient.getInstances("ESL")
        if (list != null && list.size() > 0) return list.get(0).getUri()
        else return new URI("192.168.99.100")//TODO change false TestURI
    }

    /**
     * erstellt eine HTTP Verbindung zum Rocketchat Service und Konfiguriert diese
     * @return die Http Verbindung zum Rocketchat
     */
    def connectToESL() {
        def timeOut = 10000
        HTTPBuilder http = new HTTPBuilder("http://" + getESLUri().getHost() + ":3000")
        http.client.params.setParameter('http.connection.timeout', new Integer(timeOut))
        http.client.params.setParameter('http.socket.timeout', new Integer(timeOut))
        return http
    }

    /**
     * Methode um eine Rest-GET Abfrage an den Rocketchat zu schicken
     * @param path Anfragepfad
     * @param headers HTTP header als MAP
     * @return response Inhalt der Anfrage
     */
    def getFromESL(path, Map headers) {
        return getFromESL(path, headers, new HashMap())
    }

    /**
     * Methode um eine Rest-GET Abfrage an den Rocketchat zu schicken
     * @param path Anfragepfad
     * @param headers HTTP header als MAP
     * @param param Pfadparameter der Anfrage
     * @return response Inhalt der Anfrage
     */
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

    /**
     * Methode um eine Rest-GET Abfrage an den Rocketchat zu schicken
     * @param path Anfragepfad
     * @return response Inhalt der Anfrage
     */
    def getFromESL(path) {
        return getFromESL(path, new HashMap())
    }

    /**
     * Methode um eine Rest-Post Abfrage an den Rocketchat zu schicken
     * @param body zu postende Daten
     * @param path Anfragepfad
     * @param headers HTTP header als MAP
     * @param param Pfadparameter der Anfrage
     * @return
     */
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

    /**
     * Methode um eine Rest-Post Abfrage an den Rocketchat zu schicken
     * @param body zu postende Daten
     * @param path Anfragepfad
     * @param headers HTTP header als MAP
     * @return
     */
    def postToESL(body, path, Map headers) {
        return postToESL(body, path, headers, new HashMap())
    }

    /**
     * Methode um eine Rest-Post Abfrage an den Rocketchat zu schicken
     * @param body zu postende Daten
     * @param path Anfragepfad
     * @return
     */
    def postToESL(body, path) {
        return postToESL(body, path, new HashMap())
    }

    /**
     * Methode die eine Msg zum Rocketchat anhand einer Emailadresse sendet
     * @param mail zu der die Msg gesendet werden soll
     * @param msg nachricht
     * @return boolean ob die Nachricht gesendet werden konnte oder nicht
     */
    boolean sendMsg(mail, msg) {
        return machineMethod(mail, msg)
    }

    /**
     * Methode die EineStatemachine für das Mailversenden Initiert und
     * versucht eine mail zu versenden
     * @param mail zu der die Msg gesendet werden soll
     * @param msg nachricht
     * @return boolean ob die Nachricht gesendet werden konnte oder nicht
     */
    boolean machineMethod(String mail, msg) {
        //
        eS = new MyState(funct: { http = null; return false })
        fS = new MyState(funct: { http = null; return true })
        aS = null
        cT = ["Content-type": "application/json"]
        //Initialer State im  Aktuellen State initieren
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

    /**
     * State Klasse für die Machine Mode Statemachine
     */
    class MyState {
        Closure funct
        def msg, data, mail, name
        def stateRun() {
            funct()
        }
    }

    /**
     * Statische Methode um sich auf den Chatserver Anzumelden
     * um die Html Befehle mit Sicherheitstoken ausführen zu kennen
     * @return der neue State
     */
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

    /**
     * Methode zum Überprüfen ob der Nutzer bereits existiert
     * @return der neue State
     */
    MyState doUserExist() {
        def result = getFromESL("/api/v1/users.info", cT, ["username": aS.name])
        if (result.success == true) {
            return new MyState(funct: { return doChannelExists() }, msg: aS.msg, mail: aS.mail, name: aS.name)
        } else {
            return new MyState(funct: { return makeUser() }, msg: aS.msg, mail: aS.mail, name: aS.name)

        }

    }

    /**
     * Methode zum erstellen eines Users
     * @return der neue State
     */
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

    /**
     * Methode ob ein Chat existiert
     * @return der neue State
     */
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

    /**
     * Methode um eine Nachricht zum Server zu senden
     * @return der neue State
     */
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

    /**
     * Methode zum erstellen eines neuen Servers
     * @return der neue State
     */
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
