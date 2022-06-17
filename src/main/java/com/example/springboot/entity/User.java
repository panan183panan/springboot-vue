package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author panan
 * @since 2022-03-18
 */

@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("昵称")
      private String nickname;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("电话")
      private String phone;

      @ApiModelProperty("地址")
      private String location;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

//      @ApiModelProperty("头像")
//      private String avatarUrl;
//
//      public String getAvatarUrl() {
//        return avatarUrl;
//    }
//
//      public void setAvatarUrl(String avatarUrl) {
//        this.avatarUrl = avatarUrl;
//    }

      public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
      public String getUsername() {
        return username;
    }

      public void setUsername(String username) {
          this.username = username;
      }
      public String getPassword() {
        return password;
    }

      public void setPassword(String password) {
          this.password = password;
      }
      public String getNickname() {
        return nickname;
    }

      public void setNickname(String nickname) {
          this.nickname = nickname;
      }
      public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }

      public String getPhone() {
        return phone;
    }

      public void setPhone(String phone) {
          this.phone = phone;
      }

      public String getLocation() {
        return location;
    }

      public void setLocation(String location) {
          this.location = location;
      }

      public LocalDateTime getCreateTime() {
        return createTime;
    }

      public void setCreateTime(LocalDateTime createTime) {
          this.createTime = createTime;
      }

}
