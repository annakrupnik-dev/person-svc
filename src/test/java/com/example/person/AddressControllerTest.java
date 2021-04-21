package com.example.person;

import com.example.JUnitTestUtil;
import com.example.JsonUtils;
import com.example.person.domain.Address;
import com.example.person.domain.AddressDataWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressControllerTest {

    private JUnitTestUtil testUtil = new JUnitTestUtil();

    @Test
    public void getAllAddresses()  {

        String url ="/addresses";
        String body = testUtil.executeGetRequest(url);
        Assert.assertNotNull(body);

        AddressDataWrapper obj= JsonUtils.fromJsonString(body,AddressDataWrapper.class);

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

        Address obj= JsonUtils.fromJsonString(body,Address.class);

        Assert.assertEquals(obj.getZipcode(), address.getZipcode());
    }

    @Test
    public void updateAddresses()  {

        String url ="/addresses/5";
        Address address = new Address("Yellow","Haifa","Israel",790);

        String body = testUtil.executePutRequest(url,address);

        Assert.assertNotNull(body);

        Address obj= JsonUtils.fromJsonString(body,Address.class);

        Assert.assertEquals(obj.getZipcode(), address.getZipcode());
    }

    @Test
    public void deleteAddresses()  {

        String url ="/addresses/7";

        String body = testUtil.executeDeleteRequest(url);

    }

}
