//package com.tbless.inventoryManagementApp.config;
//
//import com.cloudinary.Cloudinary;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class CloudinaryConfig {
//    private static final String CLOUD_NAME = "diqrpselr";
//    private static final String API_KEY = "598739821646194";
//    private static final String API_SECRET = "O4GCwL7sTVNvWm9vh-oRBdbG8JU";
//    @Bean
//    public Cloudinary cloudinary(){
//        Map<String, String> config = new HashMap<>();
//        config.put("cloud_name",CLOUD_NAME);
//        config.put("api_key",API_KEY);
//        config.put("api_secret",API_SECRET);
//
//        return new Cloudinary(config);
//    }
//}