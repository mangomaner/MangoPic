package com.mango.mangopic.controller;

import com.mango.mangopic.common.BaseResponse;
import com.mango.mangopic.common.ResultUtils;
import com.mango.mangopic.exception.ErrorCode;
import com.mango.mangopic.exception.ThrowUtils;
import com.mango.mangopic.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.mango.mangopic.model.entity.User;
import com.mango.mangopic.model.vo.space.analyze.SpaceUsageAnalyzeResponse;
import com.mango.mangopic.service.SpaceAnalyzeService;
import com.mango.mangopic.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/space/analyze")
public class SpaceAnalyzeController {

    @Resource
    private SpaceAnalyzeService spaceAnalyzeService;

    @Resource
    private UserService userService;

    /**
     * 获取空间使用状态
     */
    @PostMapping("/usage")
    public BaseResponse<SpaceUsageAnalyzeResponse> getSpaceUsageAnalyze(
            @RequestBody SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest,
            HttpServletRequest request
    ) {
        ThrowUtils.throwIf(spaceUsageAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        SpaceUsageAnalyzeResponse spaceUsageAnalyze = spaceAnalyzeService.getSpaceUsageAnalyze(spaceUsageAnalyzeRequest, loginUser);
        return ResultUtils.success(spaceUsageAnalyze);
    }
}

