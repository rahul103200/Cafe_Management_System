package com.in.cafe.POJO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@NamedQuery(name = "User.findByEmailId" ,query = "select u from User u where u.email=:email")

@NamedQuery(name = "User.getAllUser" ,query = "select new com.in.cafe.wrapper.UserWrapper(u.Id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'")

@NamedQuery(name = "User.getAllAdmin" ,query = "select u.email from User u where u.role='admin'")

@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.Id=:Id")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Name")
    private String name;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    @Column(name = "Status")
    private String status;

    @Column(name = "Role")
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}



