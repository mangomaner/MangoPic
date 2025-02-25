package com.mango.mangopic.manager.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.mangopic.config.OssClientConfig;
import com.mango.mangopic.exception.BusinessException;
import com.mango.mangopic.exception.ErrorCode;
import com.mango.mangopic.exception.ThrowUtils;
import com.mango.mangopic.manager.OssManager;
import com.mango.mangopic.model.dto.picture.UploadPictureResult;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



@Slf4j
public abstract class PictureUploadTemplate {

    @Resource
    private OssClientConfig ossClientConfig;

    @Resource
    private OssManager ossManager;

    @Resource
    private OSS ossClient;

    /**
     * 上传图片
     *
     * @param inputSource    文件
     * @param uploadPathPrefix 上传路径前缀
     * @return
     */
    public UploadPictureResult uploadPicture(Object inputSource, String uploadPathPrefix) {
        // 1.校验图片
        validPicture(inputSource);
        // 2.图片上传地址
        String uuid = RandomUtil.randomString(16);
        String originFilename = getOriginFilename(inputSource);
        // 拼接上传路径
        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid,
                FileUtil.getSuffix(originFilename));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);
        File file = null;
        try {
            // 3.创建临时文件
            file = File.createTempFile(uploadPath, null);
            // 处理文件来源
            processFile(inputSource, file);
            // 4.上传图片到对象存储并返回
            return buildResult(uploadPath, originFilename, file);
        } catch (Exception e) {
            log.error("图片上传到对象存储失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            this.deleteTempFile(file);
        }
    }

    /**
     * 封装返回结果
     * @param uploadPath
     * @param originFilename
     * @param file
     * @return
     */
    private UploadPictureResult buildResult(String uploadPath, String originFilename, File file) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
        BufferedImage img = ImageIO.read(input);

        String thumbnailPath = uploadPath.replaceAll("\\.[^.]+$", ".webp");

        // 封装返回结果
        UploadPictureResult uploadPictureResult = new UploadPictureResult();
        int picWidth = img.getWidth(null);
        int picHeight = img.getHeight(null);
        double picScale = NumberUtil.round(picWidth * 1.0 / picHeight, 2).doubleValue();
        uploadPictureResult.setPicName(FileUtil.mainName(originFilename));
        uploadPictureResult.setPicWidth(picWidth);
        uploadPictureResult.setPicHeight(picHeight);
        uploadPictureResult.setPicScale(picScale);
        uploadPictureResult.setPicFormat(FileUtil.extName(originFilename));
        uploadPictureResult.setPicSize(FileUtil.size(file));
        uploadPictureResult.setUrl(ossClientConfig.getUrlPrefix() + uploadPath);
        if(uploadPictureResult.getPicSize() > 2000){
            double scaleRatio;
            // 判断是宽度还是高度更长，并计算缩放比例
            scaleRatio = picWidth > picHeight ? 480.0 / picWidth : 480.0 / picHeight;
            int targetWidth = (int) (picWidth * scaleRatio);
            int targetHeight = (int) (picHeight * scaleRatio);

            // 使用ByteArrayOutputStream接收Thumbnailator生成的图像数据
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(img)
                    .size(targetWidth, targetHeight)
                    .outputFormat("webp")
                    .toOutputStream(outputStream);

            // 将ByteArrayOutputStream转换为byte数组
            byte[] imageBytes = outputStream.toByteArray();

            // 创建临时文件并将其作为File对象返回
            File webpFile = File.createTempFile("thumbnail", ".webp");
            webpFile.deleteOnExit(); // 程序退出时删除临时文件

            try (FileOutputStream fos = new FileOutputStream(webpFile)) {
                fos.write(imageBytes);
            }

            uploadPictureResult.setThumbnailUrl(ossClientConfig.getUrlPrefix() + thumbnailPath);
            ossManager.putPictureObject(thumbnailPath, webpFile);
        }

        ossManager.putPictureObject(uploadPath, file);

        return uploadPictureResult;
    }

    /**
     * 校验输入源（本地文件或 URL）
     */
    protected abstract void validPicture(Object inputSource);

    /**
     * 获取输入源的原始文件名
     */
    protected abstract String getOriginFilename(Object inputSource);

    /**
     * 处理输入源并生成本地临时文件
     */
    protected abstract void processFile(Object inputSource, File file) throws Exception;

    /**
     * 删除临时文件
     */
    public void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        // 删除临时文件
        boolean deleteResult = file.delete();
        if (!deleteResult) {
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }

}
