package cn.hbmz.password.service.impl;

import cn.hbmz.password.entiy.Users;
import cn.hbmz.password.mapper.PasswordMapper;
import cn.hbmz.password.service.PasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl extends ServiceImpl<PasswordMapper, Users> implements PasswordService{

}
