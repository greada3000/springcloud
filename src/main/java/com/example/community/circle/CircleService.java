package com.example.community.circle;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class CircleService {
  private final CircleMapper mapper;
  public Circle get(int id){return mapper.selectById(id);} public List<Circle> byOwner(int id){return mapper.selectList(new LambdaQueryWrapper<Circle>().eq(Circle::getOwner,id));}
  public Page<Circle> search(PageRequestDto r){String q=r.safeQuery();var w=new LambdaQueryWrapper<Circle>().orderByAsc(Circle::getCircleId);if(!q.isBlank()){w.like(Circle::getCircleName,q);if(q.chars().allMatch(Character::isDigit))w.or().eq(Circle::getCircleId,Integer.valueOf(q));}return mapper.selectPage(Page.of(r.page(),r.size()),w);}
  public Circle save(Circle c){if(c.getCircleId()==null)mapper.insert(c);else mapper.updateById(c);return c;} public boolean delete(int id){return mapper.deleteById(id)>0;}
}
