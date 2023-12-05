package com.gavinjin.wsdvs.controller;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import com.gavinjin.wsdvs.model.domain.User;
import com.gavinjin.wsdvs.model.dto.BaseResponse;
import com.gavinjin.wsdvs.service.ExtendedStreamingHistoryService;
import com.gavinjin.wsdvs.service.UserService;
import com.gavinjin.wsdvs.utils.ResponseUtils;
import com.gavinjin.wsdvs.utils.constant.StatusCode;
import com.gavinjin.wsdvs.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.gavinjin.wsdvs.utils.constant.UserConstant.DB_TABLE_HISTORY;
import static com.gavinjin.wsdvs.utils.constant.UserConstant.VALID_COMPRESSED_FILE_SUFFICES;

@RestController
@RequestMapping("/history")
@Slf4j
public class HistoryController {
    @Resource
    private UserService userService;

    @Resource
    private ExtendedStreamingHistoryService extendedStreamingHistoryService;

    private static final int BATCH_SIZE = 1024;

    @PostMapping("/upload-extended-streaming-history")
    public BaseResponse<Boolean> uploadExtendedStreamingHistory(@RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(StatusCode.PARAMS_ERROR, "Empty http request");
        }
        User user = userService.getLoggedInUser(request);
        String tableName = DB_TABLE_HISTORY + user.getId();

        // First, check if the file is a valid zip/rar
        String filename = multipartFile.getOriginalFilename();
        String suffix = FileUtil.getSuffix(filename);
        if (!VALID_COMPRESSED_FILE_SUFFICES.contains(suffix)) {
            return ResponseUtils.error(StatusCode.PARAMS_ERROR.getCode(), "Uploaded file is not a compressed file format");
        }

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
                if (path.startsWith("__MACOSX") || path.length() < folderName.length() || !path.substring(folderName.length()).contains("Streaming_History_Audio")) {
                    continue;
                }

                extendedStreamingHistoryService.dropPreviousExtendedStreamingHistoryTable(tableName);
                extendedStreamingHistoryService.createNewExtendedStreamingHistoryTable(tableName);

                // Parse data here
                ObjectMapper objectMapper = new ObjectMapper();
                JsonFactory jsonFactory = objectMapper.getFactory();
                JsonParser jsonParser = jsonFactory.createParser(zipInputStream);

                if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                    List<ExtendedStreamingHistorySong> list = new ArrayList<>();

                    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                        JsonNode jsonNode = objectMapper.readTree(jsonParser);

                        ExtendedStreamingHistorySong song = new ExtendedStreamingHistorySong();
                        song.setTrackUri(jsonNode.get("spotify_track_uri").asText());
                        song.setTimestamp(jsonNode.get("ts").asText());
                        song.setMillisecondsPlayed(jsonNode.get("ms_played").asLong());

                        list.add(song);
                        if (list.size() == BATCH_SIZE) {
                            extendedStreamingHistoryService.insertAllHistories(tableName, list);
                            list.clear();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(StatusCode.SYSTEM_ERROR.getCode(), "Fail to unzip the uploaded file");
        }

        return ResponseUtils.success(true);
    }
}
