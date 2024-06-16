package org.wabc.commons.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Class description.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-03-03
 */
public class AuthorizationRequestTest {
    @Test
    public void postOuathToken(){
        // http://localhost:8002/authorization/oauth/token?client_id=client_id_01&client_secret=
        // $10$ExMtq3Z1FPp6nD62uf//AuDXLU.OYHP5AOtvX14WzeiiCM2kVld3.
        // &grant_type=password&username=admin&password=123456
        String tokenUrl = "http://localhost:8002/authorization/oauth/token";
        String clientId = "client_id_01";
        String clientSecret = "$10$ExMtq3Z1FPp6nD62uf//AuDXLU.OYHP5AOtvX14WzeiiCM2kVld3.";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }
}
