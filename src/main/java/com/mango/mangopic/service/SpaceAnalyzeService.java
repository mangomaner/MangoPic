package com.mango.mangopic.service;

import com.mango.mangopic.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.mango.mangopic.model.entity.User;
import com.mango.mangopic.model.vo.space.analyze.SpaceUsageAnalyzeResponse;

public interface SpaceAnalyzeService {
    /**
     * 获取空间使用分析数据
     *
     * @param spaceUsageAnalyzeRequest SpaceUsageAnalyzeRequest 请求参数
     * @param loginUser                当前登录用户
     * @return SpaceUsageAnalyzeResponse 分析结果
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);
}
