package com.sdet.framework.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDbUtils {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static void connectDB() {
        if (mongoClient == null) {
            // Reads from local config.properties OR Jenkins system property automatically
            String connectionString = ConfigReader.getProperty("mongo.uri");
            String databaseName = ConfigReader.getProperty("mongo.db.name");

            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(databaseName);
        }
    }

    public static String getProductPriceById(String productId) {
        connectDB();
        MongoCollection<Document> collection = database.getCollection("products");
        Document product = collection.find(new Document("_id", new ObjectId(productId))).first();

        if (product != null && product.containsKey("price")) {
            return product.get("price").toString();
        }
        return null;
    }

    public static void closeDB() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}