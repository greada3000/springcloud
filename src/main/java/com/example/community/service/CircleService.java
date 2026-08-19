package com.example.community.service;

import com.example.community.dto.CircleCreateDTO;
import com.example.community.dto.CircleUpdateDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.vo.CircleVO;
import com.example.community.vo.PageVO;

import java.util.List;

public interface CircleService {
    CircleVO getCircleById(Integer circleId);

    List<CircleVO> findCirclesByOwnerId(Integer ownerId);

    PageVO<CircleVO> searchCircles(PageQueryDTO query);

    CircleVO createCircle(CircleCreateDTO input);

    CircleVO updateCircle(Integer circleId, CircleUpdateDTO input);

    void deleteCircle(Integer circleId);
}
