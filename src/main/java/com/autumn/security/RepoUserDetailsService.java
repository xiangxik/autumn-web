package com.autumn.security;

import com.autumn.modules.core.entity.User;
import com.autumn.modules.core.repo.UserRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RepoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String corpCode = obtainCorpCode(username);
        User user = userRepository.findByUsernameAndCorpCode(username, corpCode);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user " + username);
        }
        return new CustomUserDetails(user.getCorpCode(), user.getId(), user.getUsername(), user.getPassword(), true, true, true, true,
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    protected String obtainCorpCode(final String username) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String corpCode = null;
        if (requestAttributes != null) {
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                corpCode = request.getParameter(CustomUserDetails.CORP_CODE_REQUEST_PARAMETER);

                if (Strings.isNullOrEmpty(corpCode)) {
                    Cookie cookie = WebUtils.getCookie(request, CustomUserDetails.CORP_CODE_COOKIE);
                    if (cookie != null) {
                        corpCode = cookie.getValue();
                    }
                }
            }
        }

        return corpCode;
    }
}
