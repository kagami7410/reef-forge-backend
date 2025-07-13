package com.spring_ecommerce.reefForge.controllers;


import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.spring_ecommerce.reefForge.config.GcsConfig;
import com.spring_ecommerce.reefForge.models.gcsModels.FileInfoDTO;
import com.spring_ecommerce.reefForge.models.gcsModels.SignedUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/gcsBucket"))
public class GcsBucketAccessController {

    @Autowired
    Storage gcsConfig;

    @PostMapping("/get/signed-url")
    public List<SignedUrlDTO> getSignedUrl(@RequestBody List<FileInfoDTO> request) {
        request.stream().forEach(System.out::println);
        String bucketName = "fragracks-web-images";
        return request.stream().map(file -> {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, "All/" + file.fileName())
                    .setContentType(file.contentType())
                    .build();

            URL signedUrl = gcsConfig.signUrl(
                    blobInfo,
                    15,
                    TimeUnit.MINUTES,
                    Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                    Storage.SignUrlOption.withV4Signature(),
                    Storage.SignUrlOption.withContentType()
            );

            System.out.println(signedUrl);

            return new SignedUrlDTO(file.fileName(), signedUrl.toString());
        }).collect(Collectors.toList());
    }
}
