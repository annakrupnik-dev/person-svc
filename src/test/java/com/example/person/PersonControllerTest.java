package com.example.person;

import com.example.JUnitTestUtil;
import com.example.JsonUtils;
import com.example.person.domain.Gender;
import com.example.person.domain.Person;
import com.example.person.domain.PersonDataWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTest {

    private final JUnitTestUtil testUtil = new JUnitTestUtil();

    @Test
    public void getAllPersons()  {

        String url ="/addresses/persons";
        String body = testUtil.executeGetRequest(url);
        Assert.assertNotNull(body);

        PersonDataWrapper obj= JsonUtils.fromJsonString(body,PersonDataWrapper.class);

        Assert.assertTrue(obj.getPersons().size()>0);
    }

    @Test
    public void getPersonById()  {

        String url ="/addresses/persons/10";
        String body = testUtil.executeGetRequest(url);
        Assert.assertNotNull(body);

        Person obj= JsonUtils.fromJsonString(body,Person.class);
        int foundId =obj.getId();

        Assert.assertEquals(10, foundId);
    }


    @Test
    public void createPersonWithNotValidArgs()  {

        String url ="/addresses/6/persons";

        Person person = new Person("",null, null,180,70);
        boolean thrown = false;
        int statusBadRequest=400;
        int statusError=0;
        try {
            testUtil.executePostRequest(url,person);
        } catch (HttpClientErrorException e) {
            thrown = true;
            statusError = e.getStatusCode().value();
            System.out.println(e.getMessage());
        }

        Assert.assertTrue(thrown);
        Assert.assertEquals(statusBadRequest,statusError);
    }

    @Test
    public void createPerson()  {

        String url ="/addresses/5/persons";
        //Person person = new Person("Moshe",28, Gender.MALE.name(),178,68);
        //Person person = new Person("Pit",32, Gender.MALE.name(),185,80);
        Person person = new Person("Dan",4, Gender.MALE.name(),95,18);
        String body = testUtil.executePostRequest(url,person);

        Person obj= JsonUtils.fromJsonString(body,Person.class);

        Assert.assertNotNull(body);

        Assert.assertEquals(obj.getName(), person.getName());
    }

    @Test
    public void updatePerson()  {

        String url ="/addresses/5/persons/18";
        Person person = new Person("Dany",7, Gender.MALE.name(),95,18);

        String body = testUtil.executePutRequest(url,person);

        Assert.assertNotNull(body);

        Person obj= JsonUtils.fromJsonString(body,Person.class);

        Assert.assertEquals(obj.getAge(), person.getAge());
    }

    @Test
    public void updatePersonNotValidData()  {

        String url ="/addresses/50/persons/18";
        Person person = new Person("Dany",7, Gender.MALE.name(),95,18);

        boolean thrown = false;
        int statusNotFound=404;
        int statusError=0;
        try {
            testUtil.executePutRequest(url,person);
        } catch (HttpClientErrorException e) {
            thrown = true;
            statusError = e.getStatusCode().value();
            System.out.println(e.getMessage());
        }

        Assert.assertTrue(thrown);
        Assert.assertEquals(statusNotFound,statusError);
    }

    @Test
    public void deletePerson()  {

        String url ="/addresses/6/persons/17";

        String body = testUtil.executeDeleteRequest(url);

    }

}
