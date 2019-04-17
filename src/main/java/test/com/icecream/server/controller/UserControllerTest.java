package test.com.icecream.server.controller; 


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;

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
    public void testLogin() throws URISyntaxException, UnsupportedEncodingException {

        assertEquals(testLoginUtils("12345623456","nicolemayer","12345656"),"login");
        assertEquals(testLoginUtils(null,"nicolemayer","12345656"),"login");
        assertEquals(testLoginUtils("12345623456",null,"12345656"),"login");
        assertEquals(testLoginUtils(null,null,"12345656"),"fail");
        assertEquals(testLoginUtils("1234562456","nicole2mayer","1235656"),"fail");
    }

    public String testLoginUtils(String phone, String username, String password) throws URISyntaxException, UnsupportedEncodingException {
        String url = LOGIN_URL+"?phone={phone}&username={username}&password={password}";
        URI expanded = new UriTemplate(url).expand(phone, username, password); // this is what RestTemplate uses
        url = URLDecoder.decode(expanded.toString(), "UTF-8"); // java.net class

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    /**
    *
    * Method: register(@RequestParam(name="phone") String phoneNumber, @RequestParam String username, @RequestParam String password)
    *
    */
    @Test
    public void testRegister() throws Exception {
        assertEquals(testRegisterUntils("12345623456","nicolemayer", "123123243545665768798"),"password too long");
        assertEquals(testRegisterUntils("12345623456","nicolemayer", "123"),"password too short");
        assertEquals(testRegisterUntils("12345623456","nicolemayer", ""),"Empty password");

        assertEquals(testRegisterUntils("12345623456","", "123"),"Empty username");
        assertEquals(testRegisterUntils("12345623456","ni", "123454565"),"username too short");
        assertEquals(testRegisterUntils("12345623456","ni34354546576u87798", "123454565"),"username too long");

        assertEquals(testRegisterUntils("12345623456","nicolemayer", "12345656"),"User saved");

        assertEquals(testRegisterUntils("12345623456","nicolemayer2", "12345656"),"Phone Number already registered");
        assertEquals(testRegisterUntils("12345623457","nicolemayer", "12345656"),"Username has already been used");


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
