package com.tbless.inventoryManagementApp.data.models.payStack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component

//public class PayStackConfiguration {
//
////    @Value("${paystack.api.key}")
////    private String apiKey = "sk_live_dfe8b8b0b3b0088e0af45f4dfb4790f1ba278b25";
//    private String apiKey = "sk_test_4dc3d84b061b33f229791b7988cd6b0a9d6423c1";
//
//}

//import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.context.annotation.Configuration;

@Configuration
public class PayStackConfiguration {

    @Value("${paystack.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
