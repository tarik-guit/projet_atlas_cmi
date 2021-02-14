package com.cmi.cmi.service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin()
public class payfacture {

    @Autowired
    private catchjwt jwtc;
    @Autowired
    private RestTemplate restTemplate;


    public JSONObject verifiersolde(@PathVariable String tel,@PathVariable int prix) {
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        this.jwtc.avoirjwt();
        headers.add("Authorization", this.jwtc.getJwt_ml_f());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(
                "http://localhost:8002/soldesuffisant/"+tel+"/"+prix, HttpMethod.GET,entity, JSONObject.class).getBody();


    }
       @GetMapping("/payerfacture/{tel}/{prix}")
    public aide payer(@PathVariable String tel, @PathVariable int prix) {
        HttpHeaders headers = new HttpHeaders();
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        this.jwtc.avoirjwt();
        headers.add("Authorization", this.jwtc.getJwt_ml_f());
        HttpEntity<String> entity = new HttpEntity<String>(headers);
       if(this.verifiersolde(tel,prix).get("suffisant").toString().equals("0")){return new aide(0);}
       else {
           restTemplate.exchange(
                   "http://localhost:8002/retranchersomme/" + tel + "/" + prix, HttpMethod.GET, entity, JSONObject.class).getBody();


           return new aide(1);

       }
    }

}
