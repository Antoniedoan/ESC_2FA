package com.example.prince.cse;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by doanthanh on 3/4/18.
 */

public class UsernameParaTest {
    private String username;
    private boolean valid;

    private Register register;

    public UsernameParaTest(boolean valid, String username){
        this.valid = valid;
        this.username = username;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList (new Object [][] {
                // valid usernames
                {true,"abcdefg"},
                {true, "ABCDEF"},
                {true, "a23qwwe1qwertyu"},
                {true, "ASQWE1seqw12SD"},
                // invalid emails
                {false,"12sdfds"},
                {false, "1WE@#!S"},
                {false, "!@SDsdfds"},
                {false, "hi@gmail"},
                {false, "  "},
                {false, "cse"},
                {false, "myproject@gmail.com.12a"},
                {false, "thisisme..    sdfvfdc??"},
                {false, "onelastime@gmailAAAAAAAasdsa??????????????????"},
        });
    }

    @Test
    public void testUsername(){
        assertEquals(this.valid, Register.isValidUsername(username));
    }

}
