package com.example.person;

import com.example.JUnitTestUtil;
import com.example.JsonUtils;
import com.example.person.domain.Address;
import com.example.person.domain.AddressDataWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressControllerTest {

    private final JUnitTestUtil testUtil = new JUnitTestUtil();

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
        //Address address = new Address("Blue","Paris","France",1020);
        Address address = new Address("Orange","Toronto","Kanada",2020);

        String body = testUtil.executePostRequest(url,address);

        Assert.assertNotNull(body);

        Address obj= JsonUtils.fromJsonString(body,Address.class);

        Assert.assertEquals(obj.getZipcode(), address.getZipcode());
    }

    @Test
    public void updateAddresses()  {

        String url ="/addresses/27";
        Address address = new Address("Orange","Toronto","Canada",2020);

        String body = testUtil.executePutRequest(url,address);

        Assert.assertNotNull(body);

        Address obj= JsonUtils.fromJsonString(body,Address.class);

        Assert.assertEquals(obj.getZipcode(), address.getZipcode());
    }

    @Test
    public void deleteAddresses()  {

        String url ="/addresses/26";

        String body = testUtil.executeDeleteRequest(url);

    }

}
