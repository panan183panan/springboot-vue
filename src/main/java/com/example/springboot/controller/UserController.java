package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.IUserService;
import com.example.springboot.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import static com.example.springboot.common.Result.success;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author panan
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //新增或更新
    @PostMapping
    private Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));
    }

    //注册信息
    @PostMapping("/register")
    private Result register(@RequestBody UserDTO userDTO){
        String username=userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return success(userService.register(userDTO));
    }


    //登录请求数据
    @PostMapping("/login")
    private Result login(@RequestBody UserDTO userDTO){
        String username=userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return success(dto);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }


    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/page")
    public Result findPage( @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "")  String email,
                                @RequestParam(defaultValue = "")  String location) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        if(!"".equals(location)){
            queryWrapper.like("location",location);
        }
        queryWrapper.orderByDesc("id");

        //获取当前用户信息
        User currentUser = TokenUtil.getCurrentUser();
        System.out.println("============================================"+currentUser.getNickname()+"============================================");


        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    //导出为Excel文件
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        //从数据库中查询所有数据
        List<User> list = userService.list();

        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题别名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮件");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("location","地址");
        writer.addHeaderAlias("createTime","创建时间");
//        writer.addHeaderAlias("avatarUrl","头像");

        //一次性写出list内的对象到Excel，使用默认样式，强制输出标题
        writer.write(list,true);

        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");


        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        // 关闭writer，释放内存
        out.close();
        writer.close();
        //此处记得关闭输出Servlet流
//        IoUtil.close(out);
    }

    //导入到数据库中
    @PostMapping("import")
    public boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //（表头必须为英文）
//        List<User> list = reader.readAll(User.class);

        //忽略表头，写死
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for(List<Object> row : list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setLocation(row.get(5).toString());
            users.add(user);
        }
        userService.saveBatch(users);
        System.out.println(users);
        return true;
    }

}
