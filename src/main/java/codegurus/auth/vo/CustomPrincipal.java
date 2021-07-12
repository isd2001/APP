package codegurus.auth.vo;

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

        // 우리는 "ROLE_" 관리를 하지 않을 것이지만, 최소한 하나는 있어야 할 것 같아서 임의로 작성해 줌.
        return Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"));
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
