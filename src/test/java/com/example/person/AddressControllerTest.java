package com.example.person;

import com.example.JUnitTestUtil;
import com.example.JsonUtils;
import com.example.person.domain.Address;
import com.example.person.domain.AddressDataWrapper;
import com.example.person.domain.PersonDataWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressControllerTest {

    private JUnitTestUtil testUtil = new JUnitTestUtil();

    @Test
    public void test(){
        int x=2;
        int y=23;

        Assert.assertEquals(25, x+y);
        Assert.assertEquals(46, x*y);
    }
    @Test
    public void getAllAddresses()  {

        String url ="/addresses";
        String body = testUtil.executeGetRequest(url);
        Assert.assertNotNull(body);

        AddressDataWrapper obj=new AddressDataWrapper();
        ObjectMapper mapper = new ObjectMapper();

        //JSON string to Java Object

        try {
            obj = mapper.readValue(body, AddressDataWrapper.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(obj.getAddresses().size()>0);
    }
    @Test
    public void createAddresses()  {

        String url ="/addresses";
        //Address address = new Address("Green","Tel Aviv","Israel",999);
        //Address address = new Address("Yellow","Raanana","Israel",998);
        //Address address = new Address("Red Apple","New York","USA",1000);
        Address address = new Address("Blue","Paris","France",1020);

        String body = testUtil.executePostRequest(url,address);

        Assert.assertNotNull(body);

        Address obj=new Address();
        ObjectMapper mapper = new ObjectMapper();

        //JSON string to Java Object

        try {
            obj = mapper.readValue(body, Address.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(obj.getZipcode(), address.getZipcode());
    }

    @Test
    public void updateAddresses()  {

        String url ="/addresses/50";
        //Address address = new Address("Green","Tel Aviv","Israel",999);
        Address address = new Address("Yellow","Haifa","Israel",990);
        //Address address = new Address("Red Apple","New York","USA",1000);
        //Address address = new Address("Blue","Paris","France",1020);

        String body = testUtil.executePutRequest(url,address);

        Assert.assertNotNull(body);

        Address obj=new Address();
        ObjectMapper mapper = new ObjectMapper();

        //JSON string to Java Object

        try {
            obj = mapper.readValue(body, Address.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(obj.getZipcode(), address.getZipcode());
    }
}
