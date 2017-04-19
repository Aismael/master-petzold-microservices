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
 * Created by Martin Petzold on 04.12.2016.
 * DataLoader Initialisiert die Daten des Billing-Services anhand der Daten,
 * die es per Rest Anfragen vom Order Service bekommt,
 * und Erstellt zusätzlich eigene Initialdaten
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

    /**
     * Automatische Methode zum Erstellen von Initialdaten
     * @param args
     * @throws Exception
     */
    @Override
    void run(ApplicationArguments args) throws Exception {
        Bank b = new Bank(answertime: 200, name: "AmericanStandard")
        Bank b2 = new Bank(answertime: 250, name: "OrderFastBank")
        Bank b3 = new Bank(answertime: 150, name: "Payfal")
        Bank b4 = new Bank(answertime: 100, name: "Standard")
        bankRepository.saveAndFlush(b)
        bankRepository.saveAndFlush(b2)
        bankRepository.saveAndFlush(b3)
        bankRepository.saveAndFlush(b4)


    }

    /**
     * Liest die Daten der Restschnittstelle des Order Services ein
     * Und erstellt daraus Service Eigene Entitäten
     * @param url die URL der Rest Schnittstellenbeschreibung des Order-Services
     * @param raw die Grund Url des Order Services für die die Schnittstellenbeschreibung gilt
     * @throws Exception Parsing, HTTP, und Entitie Speicherung
     */
    void getFromJSONUrL(URL url, URL raw) throws Exception {
        def input = null
        try {
            input = new JsonSlurper().parse(url)
        } catch (Exception e) {
            e.printStackTrace()
        }
        if (input) {
            def view = input.config.view
            def accountsJson = new JsonSlurper().parse(new URL(raw.toString() + view.account.path +
                    view.account.all.path))
            def orderJson = new JsonSlurper().parse(new URL(raw.toString() + view.order.path +
                    view.order.all.path))
            println "LoadAccounts"+accountsJson
            println "LoadOrders"+orderJson
        }
    }


}