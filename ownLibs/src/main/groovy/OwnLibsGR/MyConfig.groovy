package OwnLibsGR
/**
 * Object das genutzt wird um die Restkonfiguration zu laden
 * Created by Martin Petzold on 17.01.2017.
 */
class MyConfig {
    RestConfigLoader rp
    def config

    MyConfig() {
        rp = new RestConfigLoader()
    }

    String getConfigJson(){
        rp.makeConfigJson('application.yml')
    }
}
