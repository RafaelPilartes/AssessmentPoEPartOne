package com.mycompany.poepartone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {
    private Login login;
    
    @BeforeEach
    public void setUp() {
        login = new Login("Kyle", "Brown");
    }

    @Test
    public void testCheckUserName_Valid() {
        login.setUsername("kyl_1");
        assertTrue(login.checkUserName(), "Username should be valid");
    }

    @Test
    public void testCheckUserName_Invalid() {
        login.setUsername("kyle!!!!!!!");
        assertFalse(login.checkUserName(), "Username should be invalid");
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        login.setPassword("Ch&&sec@ke99!");
        assertTrue(login.checkPasswordComplexity(), "Password should be valid");
    }

    @Test
    public void testCheckPasswordComplexity_Invalid() {
        login.setPassword("pass");
        assertFalse(login.checkPasswordComplexity(), "Password should be invalid");
    }

     @Test
    public void testCheckCellPhoneNumber_Valid() {
        login.setCellPhone("+27673235056");
        assertTrue(login.checkCellPhoneNumber(), "Cell phone number should be valid");
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        login.setCellPhone("0673235056");
        assertFalse(login.checkCellPhoneNumber(), "Invalid cell phone number format should fail");
    }

    @Test
    public void testRegisterUser_AllValid() {
        login.setUsername("raf_");
        login.setPassword("Ch&&sec@ke99!!");
        login.setCellPhone("+27673235056");
        assertEquals("User successfully registered.", login.registerUser());
    }

    @Test
    public void testRegisterUser_InvalidUsername() {
        login.setUsername("kyle!!!!!!!");
        login.setPassword("Ch&&sec@ke99!!");
        login.setCellPhone("+27673235056");
        assertEquals("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser());
    }

    @Test
    public void testRegisterUser_InvalidPassword() {
        login.setUsername("raf_");
        login.setPassword("pass");
        login.setCellPhone("+27673235056");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", login.registerUser());
    }

    @Test
    public void testRegisterUser_InvalidCellPhone() {
        login.setUsername("raf_");
        login.setPassword("Ch&&sec@ke99!!");
        login.setCellPhone("673235056");
        assertEquals("Cell phone number incorrectly formatted international code.", login.registerUser());
    }

    @Test
    public void testLoginUser_Success() {
        login.setUsername("raf_");
        login.setPassword("Ch&&sec@ke99!");
        assertTrue(login.loginUser("raf_", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failure() {
        login.setUsername("raf_");
        login.setPassword("Ch&&sec@ke99!");
        assertFalse(login.loginUser("user", "wrongPass"));
    }

    @Test
    public void testReturnLoginStatus_Success() {
        login.setUsername("raf_");
        login.setPassword("Ch&&sec@ke99!");
        String expected = "Welcome Kyle Brown, it is great to see you again.";
        assertEquals(expected, login.returnLoginStatus("raf_", "Ch&&sec@ke99!"));
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        login.setUsername("raf_");
        login.setPassword("Ch&&sec@ke99!");
        String expected = "Username or password incorrect, please try again.";
        assertEquals(expected, login.returnLoginStatus("wrong", "wrongPass"));
    }
}
