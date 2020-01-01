/**
 * @author acaplan
 */
package com.flickr4java.flickr.test;

import com.flickr4java.flickr.*;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.test.util.FlickrStub;
import com.flickr4java.flickr.test.util.TestProperties;
import com.flickr4java.flickr.test.util.TestPropertiesFactory;
import org.testng.annotations.BeforeMethod;

/**
 * @author acaplan
 * 
 */
public class Flickr4JavaTest {

    protected IFlickr flickr;

    protected TestProperties testProperties;

    protected String apiKeyValue ;
    protected String authTokenValue ;
    protected String apiSigValue;
    /**
     * @throws FlickrException
     */
    @BeforeMethod
    public void setUp() throws FlickrException {
        testProperties = TestPropertiesFactory.getTestProperties();

        if (testProperties.isRealFlickr()) {
            REST rest = new REST();
            rest.setHost(testProperties.getHost());

            flickr = new Flickr(testProperties.getApiKey(), testProperties.getSecret(), rest);

            //setAuth(Permission.READ);
            apiKeyValue = testProperties.getApiKey();
            authTokenValue = "72157712460982692-f4f7bc43d5aa2e4e";//"72157712448744562-4ca966ba6a51680e";
            apiSigValue = "0ba9cce71bb8210feba65852b6b5971d";
        } else {
            flickr = new FlickrStub();
        }

    }

    /**
     * Set auth parameters for API calls that need it.
     * 
     * @param perms
     */

    protected void setAuth(Permission perms) {
        Auth auth = new Auth();
        auth.setPermission(perms);
        auth.setToken(testProperties.getToken());
        auth.setTokenSecret(testProperties.getTokenSecret());

        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
    }

    /**
     * Certain tests don't require authorization and calling with auth set may mask other errors.
     */
    protected void clearAuth() {
        RequestContext requestContext = RequestContext.getRequestContext();
        requestContext.setAuth(null);
    }
}
