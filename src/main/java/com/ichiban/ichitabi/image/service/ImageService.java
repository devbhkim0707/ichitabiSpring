package com.ichiban.ichitabi.image.service;

import com.ichiban.ichitabi.image.ImageOwnerType;
import com.ichiban.ichitabi.image.dto.ImageDto;
import com.ichiban.ichitabi.image.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.upload.itemImgLocation}")
    private String itemImgLocation;

    private final ImageMapper imageMapper;
    private final FileService fileService;

    public String saveUserImage(ImageDto imageDto, MultipartFile userImgFile) throws Exception {
        String oriImgName = userImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        imgName = fileService.uploadFile(itemImgLocation, oriImgName, userImgFile.getBytes());
        imgUrl = "/images/user/" + imgName;

        imageDto.setImgUrl(imgUrl);

        imageMapper.insertImageFile(imageDto);

        return imgUrl;
    }

    public String updateUserImage(Long userId, MultipartFile userImgFile) throws Exception {
        ImageDto savedUserImage = imageMapper.selectRepImage(userId, ImageOwnerType.USER);
        String imageFileName = savedUserImage.getImgUrl().replaceFirst("^/images/user/", "");;

        if (!StringUtils.isEmpty(imageFileName)) {
            fileService.deleteFile(itemImgLocation + "/" + imageFileName);
        }

        String oriImgName = userImgFile.getOriginalFilename();
        String imgName = fileService.uploadFile(itemImgLocation, oriImgName, userImgFile.getBytes());
        String imgUrl = "/images/user/" + imgName;

        savedUserImage.setImgUrl(imgUrl);

        imageMapper.updateImageFile(savedUserImage);

        return imgUrl;
    }

    public ImageDto selectUserImage(Long userId) {
        return imageMapper.selectRepImage(userId, ImageOwnerType.USER);
    }
}
