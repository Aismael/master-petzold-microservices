package Order.Loader

import org.springframework.context.annotation.Bean

import java.lang.reflect.Array

/**
 * Created by Aismael on 17.01.2017.
 */
class MyConfig {
    RestConfigLoader rp
    def config
    MyConfig() {
        rp = new RestConfigLoader()
        config = rp.readFile("static/config/restConfig.json")
        rp.writeFile('application.yml')
    }

    def getValueByKeyString(String Key){
        def words=Key.split('\\.')
        def searchmap= config
        words.each {word->
            searchmap=searchmap."$word"
        }
        return searchmap
    }
}
