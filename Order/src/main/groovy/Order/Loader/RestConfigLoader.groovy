package Order.Loader


import groovy.json.JsonSlurper
import org.yaml.snakeyaml.Yaml;

/**
 * Created by Aismael on 17.01.2017.

 * @author Arpit Mandliya
 */
class RestConfigLoader {
    def readFile(String fileName) throws IOException
    {
        ClassLoader classLoader = this.getClass().getClassLoader()
        File configFile=new File(classLoader.getResource(fileName).getFile().replaceAll("%20", " "))
        return new JsonSlurper().parseText(configFile.text)
    }

    def writeFile(String fileName){
        println "***********************"
        LinkedHashMap RESTConfigurationFile
        ClassLoader classLoader = this.getClass().getClassLoader()
        InputStream  configFile=new FileInputStream(classLoader.getResource(fileName).getFile().replaceAll("%20", " "))
        Yaml yaml = new Yaml()
        for (Object data : yaml.loadAll(configFile)) {
            if(data.get("RESTConfiguration")){
                RESTConfigurationFile = data.get("RESTConfiguration")
            }
        }
        println RESTConfigurationFile
        def builder = new groovy.json.JsonBuilder()
        builder.config(RESTConfigurationFile){}
        File OutFile=new File(classLoader.getResource("static/config/restConfig.json").getFile().replaceAll("%20", " "))
        OutFile.write(builder.toPrettyString())
        println builder
        println "***********************"
    }
}

