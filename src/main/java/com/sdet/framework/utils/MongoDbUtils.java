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

    // Your exact connection string from your backend configuration
    private static final String CONNECTION_STRING = "mongodb+srv://katiyarrzt00_db_user:DlwGGr7eRBGcAzgq@cluster0.wqfkkep.mongodb.net/MERN_E_Commerce?retryWrites=true&w=majority";
    private static final String DATABASE_NAME = "MERN_E_Commerce";

    public static void connectDB() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
    }

    public static String getProductPriceById(String productId) {
        connectDB();
        // Assuming your product collection is named "products"
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