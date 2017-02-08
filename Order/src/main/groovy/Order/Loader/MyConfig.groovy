package Order.Loader
/**
 * Created by Aismael on 17.01.2017.
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
