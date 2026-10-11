package com.sdet.framework.stepdef;

import com.sdet.framework.api.clients.ProductApiClient;
import com.sdet.framework.api.clients.UserApiClient;
import com.sdet.framework.api.pojo.ProductPojo;
import com.sdet.framework.pages.CreateOrderE2E;
import com.sdet.framework.utils.ConfigReader;
import com.sdet.framework.utils.DriverFactory;
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

public class ProductApiStepDefs {

    private final UserApiClient userApiClient = new UserApiClient();
    private final ProductApiClient productApiClient = new ProductApiClient();
    private Response response;
    private String authToken;

    @Given("admin logs in with admin credentials")
    public void adminLogsInWithAdminCredentials() {
        // 1. Fetch credentials dynamically from config.properties
        String adminEmail = ConfigReader.getProperty("admin.email");
        String adminPassword = ConfigReader.getProperty("admin.password");

        // 2. Perform login via API and capture token
        authToken = userApiClient.loginAndGetToken(adminEmail, adminPassword);
        Assert.assertNotNull(authToken, "Authentication failed! Token is null.");

        // 3. Save token to TestContext for downstream steps/UI workflows
        TestContext.setAuthToken(authToken);
    }

    @When("admin updates product {string} with a random price from allowed list: {string}")
    public void adminUpdatesProductWithRandomPrice(String productId, String pricesCsv) {

        // Traditional Java parsing for the price list
        String[] rawPrices = pricesCsv.split(",");
        List<Double> priceList = new ArrayList<>();

        for (int i = 0; i < rawPrices.length; i++) {
            String trimmed = rawPrices[i].trim();
            priceList.add(Double.parseDouble(trimmed));
        }

        // Pick a random price from the parsed list
        double selectedPrice = priceList.get(new Random().nextInt(priceList.size()));
        System.out.println(selectedPrice);
        // Save expected price to shared context for purchase validation
        TestContext.setExpectedPrice(selectedPrice);

        // Build product payload
        ProductPojo payload = new ProductPojo();
        payload.setTitle("Iphone 16");
        payload.setDescription("World's fastest and most reliable phone");
        payload.setPrice(selectedPrice);
        payload.setCategory("Phone");
        payload.setQty(12);
//        payload.setRam("2gb");
        payload.setImgSrc("https://www.apple.com/newsroom/images/2024/09/apple-debuts-iphone-16-pro-and-iphone-16-pro-max/article/Apple-iPhone-16-Pro-hero-geo-240909_inline.jpg.large.jpg");

        // Execute authenticated PUT request
        response = productApiClient.updateProduct(productId, payload, authToken);
    }

    @Then("the product API update response code should be {int}")
    public void verifyApiResponseCode(int expectedStatusCode) {
        // Print the server's error message to your console so you can see the exact validation failure
        System.out.println(">>> API Error Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "API status code mismatch!");
    }
    @And("user navigates to product details page for {string}")
    public void userNavigatesToProductPage(String productId) {
        String baseUrl = ConfigReader.getProperty("baseUrl"); // e.g., https://mern-ecommerce-front-end.vercel.app

        // Combines base URL with your specific product path
       getDriver().get(baseUrl + "/product/" + productId);
    }
    @Then("the product price on the UI should match the updated price from the API")
    public void verifyUiPriceMatchesApi() throws InterruptedException {
        // 1. Retrieve the expected price stored during the API update step
        double expectedPrice = TestContext.getExpectedPrice();
        System.out.println("Expected Price: "+expectedPrice);
        // 2. Locate the price element on the web page using XPath


        // 3. Clean up the text (remove '$' symbol and trim whitespace)
        CreateOrderE2E ce = new CreateOrderE2E();
        String uiText = ce.getPrice();
        double actualUiPrice = Double.parseDouble(uiText);
        System.out.println("Actual Price: "+ actualUiPrice);
        // 4. Assert that the UI price matches the updated API price exactly
        Assert.assertEquals(actualUiPrice, expectedPrice, 0.01,
                "Price mismatch! UI display price does not match the updated API price.");
    }
}