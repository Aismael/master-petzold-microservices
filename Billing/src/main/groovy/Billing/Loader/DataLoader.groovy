package Billing.Loader

import Billing.Entities.Account
import Billing.Entities.Position
import Billing.Entities.XOrder
import Billing.Repositories.AccountRepository
import Billing.Repositories.PositionRepository
import Billing.Repositories.XOrderRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


/**
 * Created by Aismael on 04.12.2016.
 */
@Component
class DataLoader implements ApplicationRunner {

    @Autowired
    AccountRepository accountRepository
    @Autowired
    XOrderRepository xorderRepository
    @Autowired
    PositionRepository positionRepository

    @Autowired
    DataLoader() {
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
    }

    void getFromJSONUrL(URL url,URL raw) {
        def input = new JsonSlurper().parse(url)
        def view = input.config.view
        def accountsJson = new JsonSlurper().parse(new URL(raw.toString()+view.account.path+
                view.account.all.path))
        def orderJson= new JsonSlurper().parse(new URL(raw.toString()+view.order.path+
                view.order.all.path))
        println"*************************************************"
        println accountsJson
        println orderJson
        println"*************************************************"

        accountsJson.each {
            makeAccount(it)
        }
        //orderRepository.saveAndFlush(new Order())

        orderJson.each {
            XOrder o=xorderRepository.getOne(new Long(it.id))
            it.itemSets.all.each{it2->
                o.getPositions().add(makePosition(it2))
                o.setSendDate(it2.date)
            }
            xorderRepository.saveAndFlush(o)
        }


    }

    @Transactional
    Position makePosition(it2){
        Position p=new Position()
        p.setAmmount(new BigDecimal(it2.item.one.currency))
        p.setCount(it2.count)
        p.setName(it2.item.one.name)
        return positionRepository.saveAndFlush(p)
    }


    @Transactional
     Account makeAccount(it){
        println it
        Account a=new Account()
        a.setId(it.id)
        a.setMail(it.mail)
        it.orders.all.each {it2->
            a.getXOrders().add(makeOrder(it2))
        }
        return accountRepository.saveAndFlush(a)
    }

    @Transactional
     XOrder makeOrder(it2){
        println it2
        XOrder o = new XOrder()
        o.setId(it2.id)
        return xorderRepository.saveAndFlush(o)
    }
}