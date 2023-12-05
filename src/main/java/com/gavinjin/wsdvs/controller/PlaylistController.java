package com.gavinjin.wsdvs.controller;

import com.gavinjin.wsdvs.model.domain.User;
import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.model.vo.PlaylistFeaturesVO;
import com.gavinjin.wsdvs.service.PlaylistService;
import com.gavinjin.wsdvs.service.UserService;
import com.gavinjin.wsdvs.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.gavinjin.wsdvs.utils.constant.UserConstant.DB_TABLE_PLAYLISTS;

@RestController
@RequestMapping("/playlist")
@Slf4j
public class PlaylistController {
    @Resource
    private PlaylistService playlistService;

    @Resource
    private UserService userService;

    @GetMapping("/get-avg-features")
    public BaseResponse<List<PlaylistFeaturesVO>> getAvgFeatures(HttpServletRequest request) {
        User user = userService.getLoggedInUser(request);
        String tableName = DB_TABLE_PLAYLISTS + user.getId();
        List<PlaylistFeaturesVO> res = playlistService.getPlaylistFeatures(tableName);
        return ResponseUtils.success(res);
    }
}
