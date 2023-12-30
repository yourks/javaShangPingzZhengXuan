package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserUseMapper;
import com.atguigu.spzx.manager.service.SysUserUseService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * className:{SysUserUseServiceImpl}
 */
@Service
@Transactional
public class SysUserUseServiceImpl implements SysUserUseService {

    @Autowired
    private SysUserUseMapper sysUserUseMapper;

    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);

        /**
         疑问 1。 这样不是全查 出来了吗 这种方式 肯定会对数据产生影响量大 完蛋
         我姐的 学springboot的时候不是这么做的 做完成个复习下那种模式
         * */
        List<SysUser > list =  sysUserUseMapper.findByPage(sysUserDto);

        PageInfo<SysUser> pageInfo = new  PageInfo<>(list);

        return pageInfo;
    }


    /**
     按照案例上面的来肯定出问题 都没有检测帐号是否可以注册 名字是否唯一
     按照我们的思路来
     1。检测账号名 是否可以注册
     2。可以 开启事务  注册
     3。注册成功 返回注册成功 失败也返回失败
     ！LambdaQueryWrapper 是mybatic plus 内的内容 暂时不使用 昨晚这个工程 重写的是哦路整合 并使用
     现在先使用 sql查询
     <!--
     id(不用插入 自增长),username,password,name,phone,avatar,description
     ,status（账号正常）,update_time（now（）现在）,create_time,is_deleted 0 未删除-->
     * */

    @Override
    public int saveSysUser(SysUser sysUser) {
         //1。
        List<SysUser> sysUserList = sysUserUseMapper.selectedName(sysUser.getUserName());
        //返回用户名已被使用
        if(sysUserList.size() > 0){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_REGISTERED);
        }
        //2。开启事务

        //3.注册成功 返回注册成功 失败也返回失败
        int row = sysUserUseMapper.insertUserUseMapper(sysUser);

        return row;
    }

    /**
     1.查出用户 没 抛异常
     有 修改update时间 更新数据
     这个 项目没有建立 乐观锁机制 先不管 知道有这东西
       1.查询version版本 先不管
     2.补全属性,修改时间 , 版本!
     * */

    @Override
    public int updateSysUser(SysUser sysUser) {
        int row = sysUserUseMapper.updateSysUser(sysUser);

        return row;
    }

    @Override
    public int deleteById(String id) {
        int row = sysUserUseMapper.deleteById(id);
        return row;
    }

}
