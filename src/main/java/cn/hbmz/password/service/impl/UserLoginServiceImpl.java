package cn.hbmz.password.service.impl;

import cn.hbmz.password.entiy.Role;
import cn.hbmz.password.mapper.UserLoginMapper;
import cn.hbmz.password.service.UserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, Role> implements UserLoginService {
}
