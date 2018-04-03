package com.example.prince.cse;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by doanthanh on 15/3/18.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses(EmailParameterisedTest.class)

public class TestEmail {
    public static void main(String[] args) {

        JUnit4TestAdapter suite = new JUnit4TestAdapter(TestEmail.class);
        junit.textui.TestRunner.run(suite);

    }
}
