package com.icecream.server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import static org.junit.Assert.assertEquals;


/**
* UserController Tester.
*
* @author nicolemayer
* @since <pre>Apr 17, 2019</pre>
* @version 1.0
*/
public class UserControllerTest {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private static String PROTOCOL = "http";
    private static String HOST = "localhost";
    private static String PORT = "8080";
    private static String PRE_URL = PROTOCOL + "://" + HOST + ":" + PORT + "/";

    private static String LOGIN_URL = PRE_URL + "login";
    private static String REGISTER_URL = PRE_URL + "register";

    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: login(@RequestParam(name="phone",required=false) String phoneNumber, @RequestParam(required=false) String username, @RequestParam String password)
    *
    */
    @Test
    public void testLogin() throws Exception{
        testLoginValid();
        testLoginInvalid();
    }

    public void testLoginValid() throws Exception{
        assertEquals(testLoginUtils("12345623456","12345656"),"{\"state\":\"Valid\"}");
    }

    public void testLoginInvalid() throws Exception{
        assertEquals(testLoginUtils("1234562456","1235656"),"{\"state\":\"NoSuchUser\"}");
    }

    public String testLoginUtils(String phone, String password) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> mapInfo = new LinkedMultiValueMap<String, String>();
        mapInfo.add("phone", phone);
        mapInfo.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(mapInfo, headers);

        return restTemplate.postForObject(LOGIN_URL, request, String.class);
    }

    /**
    *
    * Method: register(@RequestParam(name="phone") String phoneNumber, @RequestParam String username, @RequestParam String password)
    *
    */

    @Test
    public void testRegister() throws Exception {
        testRegisterValid();
        testRegisterInvalid();
    }


    public void testRegisterValid() throws Exception {
        assertEquals(testRegisterUntils("12345623456","nicolemayer", "12345656"),"{\"state\":\"Valid\"}");

    }

    public void testRegisterInvalid() throws Exception {
        assertEquals(testRegisterUntils("12345623456","nicolemayer", "12345656"),"{\"state\":\"DuplicatePhoneNumber\"}");
    }

    public String testRegisterUntils(String phone, String username, String password) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> mapInfo = new LinkedMultiValueMap<String, String>();
        mapInfo.add("phone", phone);
        mapInfo.add("username", username);
        mapInfo.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(mapInfo, headers);

        return restTemplate.postForObject(REGISTER_URL, request, String.class);
    }


}
