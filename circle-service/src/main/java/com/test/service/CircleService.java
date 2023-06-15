package com.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.entity.Circle;

public interface CircleService extends IService<Circle> {
    Circle getCircleById(Integer id);

    IPage<Circle> getCircleByOwnerId(Page<Circle> page, Integer ownid);

    Page<Circle> searchCircle(Page<Circle> pageParam,String query);


}
