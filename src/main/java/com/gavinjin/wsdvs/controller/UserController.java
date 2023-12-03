package com.gavinjin.wsdvs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.gavinjin.wsdvs.model.domain.SpotifyUserData;
import com.gavinjin.wsdvs.model.domain.User;
import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.model.dto.user.UserLoginRequest;
import com.gavinjin.wsdvs.model.dto.user.UserRegisterRequest;
import com.gavinjin.wsdvs.model.vo.LoginUserVO;
import com.gavinjin.wsdvs.model.vo.UserdataVO;
import com.gavinjin.wsdvs.service.UserService;
import com.gavinjin.wsdvs.utils.ResponseUtils;
import com.gavinjin.wsdvs.utils.StringUtils;
import com.gavinjin.wsdvs.utils.constant.StatusCode;
import com.gavinjin.wsdvs.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final static Set<String> validFileSuffices = new HashSet<>(Arrays.asList("zip", "rar"));

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }

        String username = request.getUsername();
        String password = request.getPassword();
        String checkPassword =request.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Parameter is empty");
        }

        long result = userService.userRegister(username, password, checkPassword);
        return ResponseUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }

        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(StatusCode.PARAMS_ERROR);
        }

        LoginUserVO loginUserVO = userService.userLogin(username, password, request);
        return ResponseUtils.success(loginUserVO);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }
        boolean result = userService.userLogout(request);
        return ResponseUtils.success(result);
    }

    @GetMapping("/get-userdata")
    public BaseResponse<UserdataVO> getUserdata(HttpServletRequest request) {
        User user = userService.getLoggedInUser(request);
        UserdataVO userdataVO = new UserdataVO();
        BeanUtil.copyProperties(user, userdataVO);
        return ResponseUtils.success(userdataVO);
    }

    @PostMapping("/upload-spotify-data")
    public BaseResponse<Boolean> uploadSpotifyData(@RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }

        String filename = multipartFile.getOriginalFilename();
        String suffix = FileUtil.getSuffix(filename);
        if (!validFileSuffices.contains(suffix)) {
            return ResponseUtils.error(StatusCode.PARAMS_ERROR.getCode(), "Uploaded file is not a compressed file format");
        }

        // JSON file that contains personal data
        final Map<String, String> personalInfo = new HashMap<String, String>(){{
            put("Inferences.json", "{}");
            put("Payments.json", "{}");
            put("UserAddress.json", "{}");
            put("Userdata.json", "{}");
        }};
        try (ZipInputStream zipInputStream = new ZipInputStream(multipartFile.getInputStream())) {
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry == null) {
                return ResponseUtils.error(StatusCode.PARAMS_ERROR.getCode(), "Uploaded file is not in a correct format");
            }

            String folderName = entry.getName();
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                String path = entry.getName();
                if (path.startsWith("__MACOSX") || path.length() < folderName.length() || !personalInfo.containsKey(path.substring(folderName.length()))) {
                    continue;
                }

                String name = path.substring(folderName.length());
                String content = new String(IOUtils.toByteArray(zipInputStream));
                if ("UserAddress.json".equals(name)) {
                    content = userService.convertUserAddressToString(content);
                }
                personalInfo.put(name, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(StatusCode.SYSTEM_ERROR.getCode(), "Fail to unzip the uploaded file");
        }

        SpotifyUserData spotifyUserData = JSONUtil.toBean(personalInfo.get("Userdata.json"), SpotifyUserData.class, true);
        User user = userService.getLoggedInUser(request);
        copyProperties(spotifyUserData, user, personalInfo);
        userService.updateById(user);

        return ResponseUtils.success(true);
    }

    /**
     * Copy values from SpotifyUserData to our User
     * @param spotifyUserData
     * @param user
     * @param personalInfo
     */
    private void copyProperties(SpotifyUserData spotifyUserData, User user, Map<String, String> personalInfo) {
        user.setSpotifyUsername(spotifyUserData.getUsername());
        user.setSpotifyEmail(spotifyUserData.getEmail());
        user.setSpotifyCountry(spotifyUserData.getCountry());
        user.setSpotifyCreatedFromFacebook(spotifyUserData.getCreatedFromFacebook());
        user.setSpotifyFacebookUid(spotifyUserData.getFacebookUid());
        user.setSpotifyBirthdate(spotifyUserData.getBirthdate());
        user.setSpotifyBirthdate(spotifyUserData.getBirthdate());
        user.setSpotifyGender(spotifyUserData.getGender());
        user.setSpotifyPostalCode(spotifyUserData.getPostalCode());
        user.setSpotifyMobileNumber(spotifyUserData.getMobileNumber());
        user.setSpotifyMobileOperator(spotifyUserData.getMobileOperator());
        user.setSpotifyMobileBrand(spotifyUserData.getMobileBrand());
        user.setSpotifyCreateTime(spotifyUserData.getCreationTime());

        user.setSpotifyAddresses(personalInfo.get("UserAddress.json"));
        user.setSpotifyInferences(personalInfo.get("Inferences.json"));
        user.setSpotifyPayments(personalInfo.get("Payments.json"));
    }
}
