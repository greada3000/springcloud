package com.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.entity.User;
import com.test.entity.UserCon;
import com.test.entity.Prelast;

import java.util.List;

public interface UserConService extends IService<UserCon> {
    /**
     * 搜索一个关注关系
     * @param prelast
     * @return
     */
    UserCon selectOneCon(Prelast prelast);

    /**
     * 搜索所有的粉丝 peruser
     */
    List<User> selectperuser(int id);

    /**
     * 搜索所有的关注 lastuser
     */
    List<User> selectlastuser(int id);


}
