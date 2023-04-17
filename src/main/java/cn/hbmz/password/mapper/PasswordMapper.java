package cn.hbmz.password.mapper;

import cn.hbmz.password.entiy.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordMapper extends BaseMapper<Users> {
}
