package de.htw.ai.kbe.songservlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

public class EchoServletTest {
	
    private App servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private final static String URITODB_STRING = "uriToDB:test.server";
    
    @Before
    public void setUp() throws ServletException {
        servlet = new App();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        config.addInitParameter("uriToDBComponent", URITODB_STRING);
        servlet.init(config); //throws ServletException
    }
    
    @Test
    public void initShouldSetDBComponentURI() {
    		assertEquals(URITODB_STRING, servlet.getUriToDB());
    }

    @Test
    public void doGetShouldEchoParameters() throws ServletException, IOException {
        request.addParameter("username", "scott");
        request.addParameter("password", "tiger");
        
        servlet.doGet(request, response);

        assertEquals("text/plain", response.getContentType());
        assertTrue(response.getContentAsString().contains("username=scott"));
        assertTrue(response.getContentAsString().contains("password=tiger"));
        assertTrue(response.getContentAsString().contains(URITODB_STRING));        
    }
    
    @Test
    public void doPostShouldEchoBody() throws ServletException, IOException {
        request.setContent("blablablabla".getBytes());
        servlet.doPost(request, response);
        assertEquals("text/plain", response.getContentType());
        assertTrue(response.getContentAsString().contains("blablablabla"));        
    }
}
