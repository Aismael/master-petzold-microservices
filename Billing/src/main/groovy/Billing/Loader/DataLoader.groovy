package Billing.Loader

import Billing.Entities.Account
import Billing.Entities.Bank
import Billing.Entities.Position
import Billing.Entities.XOrder
import Billing.Repositories.AccountRepository
import Billing.Repositories.BankRepository
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
    BankRepository bankRepository

    @Autowired
    DataLoader() {
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
        Bank b=new Bank(answertime: 200,name: "AmericanStandard")
        Bank b2=new Bank(answertime: 250,name: "OrderFastBank")
        Bank b3=new Bank(answertime: 150,name: "Payfal")
        Bank b4=new Bank(answertime: 100,name: "Standard")
        bankRepository.saveAndFlush(b)
        bankRepository.saveAndFlush(b2)
        bankRepository.saveAndFlush(b3)
        bankRepository.saveAndFlush(b4)


    }

    void getFromJSONUrL(URL url,URL raw) throws Exception {
        def input=null
        try {
             input = new JsonSlurper().parse(url)
        }catch (Exception e){

        }
        if(input){
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
            }
            println "##############"
            println it
            println "##############"
            xorderRepository.saveAndFlush(o)
        }

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
        XOrder o;
        a.setId(it.id)
        a.setMail(it.mail)
        it.orders.all.each {it2->
            o=makeOrder(it2)
            a.getXOrders().add(o)
            o.getAccount().add(a)
            xorderRepository.saveAndFlush(o)
        }


        return accountRepository.saveAndFlush(a)

    }

    @Transactional
     XOrder makeOrder(it2){


        XOrder o = new XOrder()
        o.setId(it2.id)
        if(it2.date) {
            o.setSendDate(new Date((Long) it2.date))
        }

        return xorderRepository.saveAndFlush(o)
    }
}