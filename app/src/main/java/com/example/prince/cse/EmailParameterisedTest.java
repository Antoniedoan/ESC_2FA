package com.example.prince.cse;

/**
 * Created by doanthanh on 15/3/18.
 */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmailParameterisedTest {
    private String email;
    private boolean valid;

    private LoginActivity loginActivity;

    public EmailParameterisedTest(boolean valid, String email){
        this.valid = valid;
        this.email = email;
    }

//    @Before
//    public void init(){
//        loginActivity = new LoginActivity();
//    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object [][] {
                // valid emails
                {true,"abc@gmail.com"},
                {true, "this.is.email@gmail.com"},
                {true, "aaa23@gmail.com"},
                // invalid emails
                {false,"mymail"},
                {false, "myname@"},
                {false, "123@."},
                {false, "hi@gmail"},
                {false, "123@#gmail.1"},
                {false, "helloworld@123@gmail.com"},
                {false, "myproject@gmail.com.12a"},
                {false, "thisisme..123@gmail.com"},
                {false, "onelastime@gmail.com.com"},
        });
    }

    @Test
    public void testEmail(){
        assertEquals(this.valid, LoginActivity.isEmailValid(email));
    }

}
