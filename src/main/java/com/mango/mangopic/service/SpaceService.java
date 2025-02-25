package com.mango.mangopic.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mango.mangopic.model.dto.space.SpaceQueryRequest;
import com.mango.mangopic.model.dto.space.SpaceAddRequest;
import com.mango.mangopic.model.entity.Space;
import com.mango.mangopic.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mango.mangopic.model.entity.User;
import com.mango.mangopic.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author mangoman
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-02-19 13:42:18
*/
public interface SpaceService extends IService<Space> {

    /**
     * 创建空间
     * @param spaceAddRequest
     * @param loginUser
     * @return
     */
    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 校验空间数据
     * @param space
     * @param add   是否为创建时校验
     */
    void validSpace(Space space, boolean add);

    /**
     * 根据空间级别自动填充限额数据
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);

    /**
     * 将查询请求转为QueryWrapper对象
     * @param spaceQueryRequest
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取单个图片封装
     * @param space
     * @param request
     * @return
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 分页获取图片封装
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 校验空间权限
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);
}
