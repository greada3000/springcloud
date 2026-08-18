package com.example.community.concern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.community.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor
public class UserConcernService {
  private final UserConcernMapper mapper; private final UserService users;
  public UserConcern relation(int follower,int followed){return mapper.selectOne(new LambdaQueryWrapper<UserConcern>().eq(UserConcern::getPreuser,follower).eq(UserConcern::getLastuser,followed));}
  public List<User> followers(int id){return mapper.selectList(new LambdaQueryWrapper<UserConcern>().eq(UserConcern::getLastuser,id)).stream().map(c->users.get(c.getPreuser())).toList();}
  public List<User> following(int id){return mapper.selectList(new LambdaQueryWrapper<UserConcern>().eq(UserConcern::getPreuser,id)).stream().map(c->users.get(c.getLastuser())).toList();}
  @Transactional public UserConcern follow(int follower,int followed){UserConcern found=relation(follower,followed);if(found!=null)return found;var c=new UserConcern();c.setPreuser(follower);c.setLastuser(followed);mapper.insert(c);return c;}
  @Transactional public boolean unfollow(int follower,int followed){return mapper.delete(new LambdaQueryWrapper<UserConcern>().eq(UserConcern::getPreuser,follower).eq(UserConcern::getLastuser,followed))>0;}
}
