package com.utils;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void testValidator_ValidInput(){
        Validator validator=new Validator();
        String test="test";
        Assert.assertTrue(validator.validateNotNull(test));
    }

    @Test
    public void testValidator_InValidInput(){
        Validator validator=new Validator();
        String test=null;
        Assert.assertFalse(validator.validateNotNull(test));
    }
}
