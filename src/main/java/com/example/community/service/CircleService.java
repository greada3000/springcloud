package com.example.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.community.entity.Circle;
import java.util.List;
public interface CircleService {
  Circle get(Integer id); List<Circle> byOwner(Integer owner); IPage<Circle> search(String keyword,long page,long size);
  Circle create(Circle value); Circle update(Integer id,Circle input); void delete(Integer id);
}
