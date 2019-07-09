package com.autumn.security;

import com.autumn.support.data.CorpDetection;
import com.google.common.base.Strings;
import org.springframework.core.NamedThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentCorpDetection implements CorpDetection {
    private static final ThreadLocal<String> corpCodeHolder = new NamedThreadLocal<>("corp");

    public static String getCorpCode() {
        return corpCodeHolder.get();
    }

    public static void setCorpCode(String corpCode) {
        corpCodeHolder.set(corpCode);
    }

    public static void cleanup() {
        corpCodeHolder.remove();
    }

    @Override
    public String getCurrentCorpCode() {

        String corpCode = getCorpCode();
        if (Strings.isNullOrEmpty(corpCode)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof CustomUserDetails) {
                    return ((CustomUserDetails) principal).getCorpCode();
                }
            }

        }

        return corpCode;
    }


}
