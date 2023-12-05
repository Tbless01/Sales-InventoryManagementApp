//package com.tbless.inventoryManagementApp.services.Cloudinary;
//
//import com.cloudinary.Cloudinary;
//import io.jsonwebtoken.io.IOException;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class CloudinaryServiceImpl implements CloudinaryService {
//    private final Cloudinary cloudinary;
//
//    //    @Override
////    public String uploadImage(MultipartFile image) throws IOException, java.io.IOException {
////        Map<String, String> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
////        return (String) uploadResult.get("url");
////    }
//    @Override
//    public String uploadImage(MultipartFile image) throws IOException, java.io.IOException {
//        return cloudinary.uploader()
//                .upload(image.getBytes(),
//                        Map.of("public.Id", UUID.randomUUID().toString()))
//                .get("url")
//                .toString();
//    }
//}
//
