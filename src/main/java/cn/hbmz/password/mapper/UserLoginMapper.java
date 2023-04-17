package cn.hbmz.password.mapper;

import cn.hbmz.password.entiy.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginMapper extends BaseMapper<Role> {
}
