package com.flickr4java.flickr.test;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.test.Controlers.textFileProvider;
import com.flickr4java.flickr.test.keywords.WebServicesKeywords;
import com.flickr4java.flickr.uploader.UploadMetaData;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static com.flickr4java.flickr.test.keywords.WebServicesKeywords.getHttpClient;
import static io.qameta.allure.Allure.step;
//TODO 1
//1)JSON  and hard and soft asserts
////complete 5 tests on test plan and on code
//TODO 2
//2)Data drriven testing
//TODO 3-DONE
//3)Abstraction
//TODO 4-DONE
//4)Allure

/**
 * @author Anthony Eden
 */
public class UploaderTest extends Flickr4JavaTest {

    private static final String DUMMY_PHOTO_ID = "1234567890";
    /**/

    /**
     * Build {@link UploadMetaData} with public set to false so uploaded photos are private.
     *
     * @return
     */
    private UploadMetaData buildPrivatePhotoMetadata() {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setPublicFlag(false);
        return uploadMetaData;
    }


    @Test(priority = 0, description = "Get the updated upload report to flicker site\n" +
            "using OkHttp")
    public static void uploadStatus() {
        step("produce request");
        Request request = WebServicesKeywords.generateOkHttpRequest();
        OkHttpClient client = getHttpClient();
        step("produce responce");
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().toString();
            System.out.println("*** Starting Hard asserting!!will verify every assert and throw exception when assert failes***");
            Assert.assertTrue(response.code() == 200, "request FAILED TO RETURN 200");
            Assert.assertTrue(response.isSuccessful() == true, "request FAILED !!");
            SoftAssert softAssert = new SoftAssert();
            System.out.println("*** Starting soft asserting!!will verify all assert only at the end(assert all)***");
            softAssert.assertTrue(response.code() == 200, "request FAILED TO RETURN 200");
            softAssert.assertTrue(response.isSuccessful() == true, "request FAILED !!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////
    //Data drriven interface based on testng listener.
    //see textFileProvider for implementation.
    //The data file(table) is located on TestDrrivenData folder
    /////////////////////////////////////////////////////
    @Test(dataProvider = "textFileNameAsMethodName",dataProviderClass= textFileProvider.class)
    @Description("get the user uload status")
    public void getUploadStatusDataDrriven(String user,String testedItem) throws IOException, ParseException {
        apiSigValue="946fbc88bd6330c529851e22b317bca3";;
        String url = "https://www.flickr.com/services/rest/?method=flickr.people.getUploadStatus"
                +"&api_key="
                + apiKeyValue
                +"&format=json&nojsoncallback=1&"
                +"auth_token="
                +authTokenValue
                +"&api_sig="
                +apiSigValue;
        String response = WebServicesKeywords.getHttpResponse(url);
        JSONParser parser = new JSONParser();
        JSONObject responceDetails = (JSONObject) parser.parse(response.toString());
        Assert.assertTrue(responceDetails.get("stat").equals("ok"), "stat <> ok");
        Assert.assertTrue(responceDetails.get("user").toString().contains("id") == true);
        Assert.assertTrue(responceDetails.get(user).toString().contains(testedItem), "method wasn't returned");
    }

    @Test(priority = 0, description = "Get the recent photos details")
    @Severity(SeverityLevel.BLOCKER)
    public void getRecentPhotos() throws IOException, FlickrException, ParseException {
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=ca370d51a054836007519a00ff4ce59e&per_page=10&format=json&nojsoncallback=1";
        String response = WebServicesKeywords.getHttpResponse(url);
        JSONParser parser = new JSONParser();
        JSONObject responceDetails = (JSONObject) parser.parse(response.toString());
        Assert.assertTrue(responceDetails.get("stat").equals("ok"), "stat <> ok");
        Assert.assertTrue(responceDetails.containsKey("photos") == true);
        Assert.assertTrue(responceDetails.get("photos").toString().contains("1000"), "method wasn't returned");
    }

    @Test(description="get the user uload status")
    public void getUploadStatus() throws IOException, ParseException {
        apiSigValue="946fbc88bd6330c529851e22b317bca3";
        String url_test="https://www.flickr.com/services/rest/?method=flickr.people.getUploadStatus&"
                +"api_key="
                +apiKeyValue//"38e8f1f81442eba74b038c854f03d52d"
                +"&format=json&nojsoncallback=1&"
                +"auth_token="
                +authTokenValue//"72157712460982692-f4f7bc43d5aa2e4e"
                +"&api_sig="
                +apiSigValue;//"946fbc88bd6330c529851e22b317bca3";
        String response = WebServicesKeywords.getHttpResponse(url_test);
        JSONParser parser = new JSONParser();
        JSONObject responceDetails = (JSONObject) parser.parse(response.toString());
        Assert.assertTrue(responceDetails.get("stat").equals("ok"), "stat <> ok");
        Assert.assertTrue(responceDetails.get("user").toString().contains("id") == true);
        Assert.assertTrue(responceDetails.get("user").toString().contains("amielnoy"), "method wasn't returned");
    }



    @Test
    public void getEcho() throws IOException, ParseException {
        String url="https://www.flickr.com/services/rest/?method=flickr.test.echo"+
                "&api_key="
                +apiKeyValue
                +"&format=json&nojsoncallback=1&"
                +"auth_token="
                +authTokenValue
                +"&api_sig="
                + apiSigValue;
        String response = WebServicesKeywords.getHttpResponse(url);

        Assert.assertNotNull(response, "echo responce is null/empty!!");
        Assert.assertTrue(false==response.contains("\"stat\":\"fail\""));
        JSONParser parser = new JSONParser();
        JSONObject responceDetails = (JSONObject) parser.parse(response.toString());
        Assert.assertTrue(responceDetails.get("stat").equals("ok"), "stat <> ok");
        Assert.assertTrue(responceDetails.containsKey("api_key") == true);
        Assert.assertTrue(responceDetails.get("api_key").toString().contains(apiKeyValue), "apiKeyValue wasn't returned");
        Assert.assertTrue(responceDetails.get("method").toString().contains("flickr.test.echo"), "method wasn't returned");
    }

}

