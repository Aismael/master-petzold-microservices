package Order.Loader

import org.yaml.snakeyaml.Yaml

/**
 * Created by Aismael on 18.01.2017.
 */
println "***********************"
LinkedHashMap RESTConfigurationFile
ClassLoader classLoader = this.getClass().getClassLoader()
InputStream  configFile=new FileInputStream(classLoader.getResource('application.yml').getFile().replaceAll("%20", " "))
println classLoader.getResource('application.yml').getFile().replaceAll("%20", " ")
Yaml yaml = new Yaml()
for (Object data : yaml.loadAll(configFile)) {
     if(data.get("RESTConfiguration")){
         RESTConfigurationFile = data.get("RESTConfiguration")
     }

}
println RESTConfigurationFile
def builder = new groovy.json.JsonBuilder()
def root = builder.config(RESTConfigurationFile){
}

//println  classLoader.getResource("static/config/restConfig.json").getFile().replaceAll("%20", " ")
//
println this.getClass().getResource('application.yml')
println "http://localhost:8080/config/restConfig.json".toURL().getFile()
File configFile2=new File(classLoader.getResource("static/config/restConfig.json").getFile().replaceAll("%20", " "))
configFile2.write(builder.toPrettyString())
println builder

println "***********************"
