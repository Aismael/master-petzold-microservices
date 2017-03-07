package OwnLibsGR


import groovy.json.JsonSlurper
import org.yaml.snakeyaml.Yaml

import java.nio.file.Files
import java.nio.file.Paths;

/**
 * lädt die Rest Konfiguration aus einer yaml Datei anhand des Dateinamens
 * Created by Martin Petzold on 17.01.2017.
 */
class RestConfigLoader {

    String makeConfigJson(String fileName){
        println "***********************"
        LinkedHashMap RESTConfigurationFile
        ClassLoader classLoader = this.getClass().getClassLoader()
        InputStream  configFile=classLoader.getResourceAsStream(fileName)
        Yaml yaml = new Yaml()
        for (Object data : yaml.loadAll(configFile)) {
            if(data.get("RESTConfiguration")){
                RESTConfigurationFile = data.get("RESTConfiguration")
            }
        }
        println RESTConfigurationFile
        def builder = new groovy.json.JsonBuilder()
        builder.config(RESTConfigurationFile){}
        println builder
        println "***********************"
        return builder.toPrettyString()

    }
}

