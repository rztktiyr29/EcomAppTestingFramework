package com.sdet.framework.stepdef;

import com.sdet.framework.api.clients.ProductApiClient;
import com.sdet.framework.api.clients.UserApiClient;
import com.sdet.framework.api.pojo.ProductPojo;
import com.sdet.framework.pages.BasePage;
import com.sdet.framework.pages.CreateOrderE2E;
import com.sdet.framework.utils.ConfigReader;
import com.sdet.framework.utils.DriverFactory;
import com.sdet.framework.utils.MongoDbUtils;
import com.sdet.framework.utils.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sdet.framework.utils.DriverFactory.getDriver;

public class ProductApiStepDefs extends BasePage {

    private final UserApiClient userApiClient = new UserApiClient();
    private final ProductApiClient productApiClient = new ProductApiClient();
    private Response response;
    private String authToken;

    @Given("admin logs in with admin credentials")
    public void adminLogsInWithAdminCredentials() {
        String adminEmail = ConfigReader.getProperty("admin.email");
        String adminPassword = ConfigReader.getProperty("admin.password");

        authToken = userApiClient.loginAndGetToken(adminEmail, adminPassword);
        Assert.assertNotNull(authToken, "Authentication failed! Token is null.");

        TestContext.setAuthToken(authToken);
    }

    @When("admin updates product {string} with a random price from allowed list: {string}")
    public void adminUpdatesProductWithRandomPrice(String productId, String pricesCsv) {
        String[] rawPrices = pricesCsv.split(",");
        List<Double> priceList = new ArrayList<>();

        for (int i = 0; i < rawPrices.length; i++) {
            String trimmed = rawPrices[i].trim();
            priceList.add(Double.parseDouble(trimmed));
        }

        double selectedPrice = priceList.get(new Random().nextInt(priceList.size()));
        System.out.println(selectedPrice);
        TestContext.setExpectedPrice(selectedPrice);

        ProductPojo payload = new ProductPojo();
        payload.setTitle("Iphone 16");
        payload.setDescription("World's fastest and most reliable phone");
        payload.setPrice(selectedPrice);
        payload.setCategory("Phone");
        payload.setQty(12);
        payload.setImgSrc("https://www.apple.com/newsroom/images/2024/09/apple-debuts-iphone-16-pro-and-iphone-16-pro-max/article/Apple-iPhone-16-Pro-hero-geo-240909_inline.jpg.large.jpg");

        response = productApiClient.updateProduct(productId, payload, authToken);
    }

    @Then("the product API update response code should be {int}")
    public void verifyApiResponseCode(int expectedStatusCode) {
        System.out.println(">>> API Error Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "API status code mismatch!");
    }

    @And("user navigates to product details page for {string}")
    public void userNavigatesToProductPage(String productId) {
        String baseUrl = ConfigReader.getProperty("baseUrl");
        getDriver().get(baseUrl + "/product/" + productId);
    }

    @Then("the product price on the UI should match the updated price from the API")
    public void verifyUiPriceMatchesApi() throws InterruptedException {
        double expectedPrice = TestContext.getExpectedPrice();
        System.out.println("Expected Price: " + expectedPrice);

        CreateOrderE2E ce = new CreateOrderE2E();
        pause(2);
        String uiText = ce.getProductPrice();
        pause(1);
        double actualUiPrice = Double.parseDouble(uiText);
        System.out.println("Actual Price: " + actualUiPrice);

        Assert.assertEquals(actualUiPrice, expectedPrice, 0.01,
                "Price mismatch! UI display price does not match the updated API price.");
    }

    @And("the product price in MongoDB is updated to match the API update for {string}")
    public void verifyProductPriceInMongoDB(String productId) {
        String dbPrice = MongoDbUtils.getProductPriceById(productId);
        Assert.assertNotNull(dbPrice, "Product not found in MongoDB for ID: " + productId);

        double expectedPrice = TestContext.getExpectedPrice();
        double actualDbPrice = Double.parseDouble(dbPrice);

        Assert.assertEquals(actualDbPrice, expectedPrice, 0.01, "MongoDB price does not match the API update!");

        System.out.println("✅ MongoDB Validation Passed! DB Price: " + dbPrice);
    }
}