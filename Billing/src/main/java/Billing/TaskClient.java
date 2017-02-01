package Billing;

import Billing.DTOs.AccountBroadcastDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;


public class TaskClient {

    public static void main(String[] args) {
        AsyncRestTemplate template = new AsyncRestTemplate();
        AccountBroadcastDto requestBody = new AccountBroadcastDto();
        HttpEntity<AccountBroadcastDto> requestEntity = new HttpEntity<AccountBroadcastDto>(requestBody);
        ListenableFuture<ResponseEntity<String>> lFutureResponse = template.postForEntity("http://localhost:8080/accountBr", requestEntity, String.class);
        lFutureResponse.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {

            @Override
            public void onSuccess(ResponseEntity<String> responseEntity) {
                System.out.println(responseEntity.getBody());
            }

            @Override
            public void onFailure(Throwable err) {
                System.out.println(err);
            }
        });

    }

}