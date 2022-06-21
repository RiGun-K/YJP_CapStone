package com.example.capstone.controller.storage;

import com.example.capstone.domain.storage.Storage;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingAreaRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Storage.*;
import com.example.capstone.repository.orders.OrdersRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StorageImageController {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private StorageManagerRepository storageManagerRepository;

    @Autowired
    private StorageBoxRepository storageBoxRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UseStorageBoxRepository useStorageBoxRepository;

    @Autowired
    KindRepository kindRepository;

    @Autowired
    MemberEquipmentRepository memberEquipmentRepository;

    @Autowired
    CampingAreaRepository campingAreaRepository;

    @GetMapping(value = "/storageImage/{code}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] imagesSearch(@PathVariable("code") long code, HttpServletResponse httpServletResponse) throws IOException {

        System.out.println(code);
        System.out.println("-------------------1----------------");

        Optional<Storage> storage = storageRepository.findById(code);

        String requestPath = System.getProperty("user.dir") + storage.get().getFilePath();
        InputStream imageStream = new FileInputStream(requestPath);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }

}
