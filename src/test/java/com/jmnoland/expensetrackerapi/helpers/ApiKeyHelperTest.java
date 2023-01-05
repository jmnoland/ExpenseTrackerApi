package com.jmnoland.expensetrackerapi.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ApiKeyHelperTest {

    @Test
    public void generateApiKey_ShouldBeSuccessful_WhenKeyLenNotNull() {
        String key = ApiKeyHelper.generateApiKey(1);

        assertNotNull(key);
        assertTrue(key.length() >= 1);
    }
}
