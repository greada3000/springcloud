package com.example.community.user;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class UserService {
  private final UserMapper mapper; private final PasswordEncoder encoder;
  public User get(int id) { return mapper.selectById(id); }
  public User login(int id, String password) { User u=get(id); return u!=null && encoder.matches(password,u.getPassword()) ? u:null; }
  public void register(User u) { if(get(u.getUserId())!=null) throw new IllegalArgumentException("账号已存在"); u.setPassword(encoder.encode(u.getPassword())); if(u.getUserpic()==null)u.setUserpic("/images/default-avatar.png"); mapper.insert(u); }
  public Page<User> search(PageRequestDto r) { String q=r.safeQuery(); var w=new LambdaQueryWrapper<User>().orderByAsc(User::getUserId); if(!q.isBlank()){w.like(User::getUsername,q);if(q.chars().allMatch(Character::isDigit))w.or().eq(User::getUserId,Integer.valueOf(q));} return mapper.selectPage(Page.of(r.page(),r.size()),w); }
  public void update(int id,User input){User u=required(id);u.setUsername(input.getUsername());u.setUsertype(input.getUsertype());u.setUserpic(input.getUserpic());mapper.updateById(u);}
  public void changePassword(int id,String oldValue,String newValue){User u=required(id);if(!encoder.matches(oldValue,u.getPassword()))throw new IllegalArgumentException("旧密码错误");u.setPassword(encoder.encode(newValue));mapper.updateById(u);}
  public boolean delete(int id){return mapper.deleteById(id)>0;}
  private User required(int id){User u=get(id);if(u==null)throw new IllegalArgumentException("用户不存在");return u;}
}
