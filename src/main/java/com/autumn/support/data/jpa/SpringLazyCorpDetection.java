package com.autumn.support.data.jpa;

import com.autumn.support.data.CorpDetection;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringLazyCorpDetection implements CorpDetection {

    private volatile CorpDetection corpDetection;

    @Override
    public String getCurrentCorpCode() {
        if (corpDetection == null) {
            synchronized (corpDetection) {
                if (corpDetection == null) {
                    ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
                    corpDetection = context.getBean(CorpDetection.class);
                    if (corpDetection == null) {
                        corpDetection = new NullCorpDetection();
                    }
                }
            }

        }
        return corpDetection.getCurrentCorpCode();
    }

    static class NullCorpDetection implements CorpDetection {

        @Override
        public String getCurrentCorpCode() {
            return null;
        }
    }
}
