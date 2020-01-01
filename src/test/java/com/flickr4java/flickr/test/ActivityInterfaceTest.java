package com.flickr4java.flickr.test;

import com.flickr4java.flickr.activity.ActivityInterface;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * 
 * @author mago
 * @version $Id: ActivityInterfaceTest.java,v 1.3 2009/06/30 18:48:59 x-mago Exp $
 */
public class ActivityInterfaceTest extends Flickr4JavaTest {

    @Test
    public void testCheckTimeframeArg() {
        ActivityInterface actInterface = flickr.getActivityInterface();
        assertTrue(actInterface.checkTimeframeArg("300d"));
    }
}
