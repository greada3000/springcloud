package com.example.community.service.impl;

import com.example.community.dto.CircleCreateDTO;
import com.example.community.dto.CircleUpdateDTO;
import com.example.community.dto.PageQueryDTO;
import com.example.community.entity.Circle;
import com.example.community.mapper.CircleMapper;
import com.example.community.service.CircleService;
import com.example.community.vo.CircleVO;
import com.example.community.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CircleServiceImpl implements CircleService {
    private final CircleMapper mapper;

    public CircleVO getCircleById(Integer circleId) {
        return CircleVO.from(requireCircle(circleId));
    }

    private Circle requireCircle(Integer circleId) {
        Circle circle = mapper.selectById(circleId);
        if (circle == null) throw new IllegalArgumentException("圈子不存在");
        return circle;
    }

    public List<CircleVO> findCirclesByOwnerId(Integer ownerId) {
        return mapper.selectByOwnerId(ownerId).stream().map(CircleVO::from).toList();
    }

    public PageVO<CircleVO> searchCircles(PageQueryDTO query) {
        long current = query.getPage();
        long pageSize = query.getSize();
        String keyword = query.getKeyword();
        String searchKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        var circles = mapper.selectPageByKeyword(searchKeyword, (current - 1) * pageSize, pageSize)
                .stream().map(CircleVO::from).toList();
        return new PageVO<>(circles,
                mapper.countByKeyword(searchKeyword), current, pageSize);
    }

    @Transactional
    public CircleVO createCircle(CircleCreateDTO input) {
        Circle circle = new Circle();
        circle.setOwner(input.owner());
        circle.setCircleName(input.circleName());
        circle.setDetail(input.detail());
        mapper.insert(circle);
        return getCircleById(circle.getCircleId());
    }

    @Transactional
    public CircleVO updateCircle(Integer circleId, CircleUpdateDTO input) {
        Circle circle = requireCircle(circleId);
        if (input.owner() != null) circle.setOwner(input.owner());
        if (input.circleName() != null) circle.setCircleName(input.circleName());
        if (input.detail() != null) circle.setDetail(input.detail());
        mapper.updateById(circle);
        return getCircleById(circleId);
    }

    @Transactional
    public void deleteCircle(Integer circleId) {
        if (mapper.deleteById(circleId) == 0) throw new IllegalArgumentException("圈子不存在");
    }
}
