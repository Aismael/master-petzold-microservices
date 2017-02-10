package Billing.Loader

import org.yaml.snakeyaml.Yaml

/**
 * Created by Aismael on 17.01.2017.

 * @author Arpit Mandliya
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

