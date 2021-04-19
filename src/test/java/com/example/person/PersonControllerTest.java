package com.example.person;

import com.example.JUnitTestUtil;
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
    public void getAllPersons(){

        String url ="http://localhost:8080/persons";
        String body = testUtil.executeGetRequest(url);

    }
}
