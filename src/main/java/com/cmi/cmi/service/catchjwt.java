package com.cmi.cmi.service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class catchjwt {
    public String getJwt_ml() {
        return jwt_ml;
    }

    public void setJwt_ml(String jwt_ml) {
        this.jwt_ml = jwt_ml;
    }

    public String getJwt_ml_f() {
        return jwt_ml_f;
    }

    public void setJwt_ml_f(String jwt_ml_f) {
        this.jwt_ml_f = jwt_ml_f;
    }

    @Autowired
    private RestTemplate restTemplate;
    private String jwt_ml;
    private String jwt_ml_f;

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public JSONObject avoirjwt() {
        JSONObject user1 = new JSONObject();
        user1.put("username", "administrateur");
        user1.put("password", "adminpassword");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(user1, headers);

        JSONObject obj = restTemplate.exchange(
                "http://localhost:8002/signin", HttpMethod.POST, entity, JSONObject.class).getBody();
        this.jwt_ml = obj.get("token").toString();
        this.jwt_ml_f = "Bearer " + jwt_ml;
        return obj;
    }
}


