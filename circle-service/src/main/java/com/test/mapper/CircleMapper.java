package com.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.entity.Circle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CircleMapper extends BaseMapper<Circle> {
}
