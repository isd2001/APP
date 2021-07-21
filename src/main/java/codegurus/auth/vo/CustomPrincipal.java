package codegurus.auth.vo;

import codegurus.cmm.jwt.TokenProvider;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * spring security 의 UserDetails interface를 구현한 UserVo의 wrapper
 *
 * @author 이프로
 * @version 2021.06
 */
@AllArgsConstructor
public class CustomPrincipal implements UserDetails {

    private UserVO userVo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Lists.newArrayList(new SimpleGrantedAuthority(TokenProvider.DEFAULT_ROLE));
    }

    @Override
    public String getPassword() {
        return userVo.getPassword();
    }

    @Override
    public String getUsername() {
        return userVo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // TODO: 사용자정보 판단 후 적용할 것인지
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO: 사용자정보 판단 후 적용할 것인지
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO: 사용자정보 판단 후 적용할 것인지
    }

    @Override
    public boolean isEnabled() {
        return true; // TODO: 사용자정보 판단 후 적용할 것인지
    }
}
