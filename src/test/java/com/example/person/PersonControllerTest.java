package com.example.person;

import com.example.JUnitTestUtil;
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
public class PersonControllerTest {

    private JUnitTestUtil testUtil = new JUnitTestUtil();

    @Test
    public void test(){
        int x=2;
        int y=23;

        Assert.assertEquals(25, x+y);
        Assert.assertEquals(46, x*y);
    }

    @Test
    public void getAllPersons()  {

        String url ="/addresses/persons";
        String body = testUtil.executeGetRequest(url);
        Assert.assertNotNull(body);

        PersonDataWrapper obj=new PersonDataWrapper();
        ObjectMapper mapper = new ObjectMapper();
        //JSON string to Java Object

        try {
            obj = mapper.readValue(body, PersonDataWrapper.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(obj.getPersons().size()>0);
    }
}
