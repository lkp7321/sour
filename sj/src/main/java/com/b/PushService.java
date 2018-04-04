package com.b;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service    // 注解本身是没什么意义的主要是将这个类的bean交给spring 来管理;否则就只有在配置类中使用@Bean来管理
public class PushService {
    private DeferredResult<String> deferredResult;

    public DeferredResult<String> getAsyncUpdate() {
       deferredResult = new DeferredResult<>();
       return deferredResult;
    }
    @Scheduled(fixedDelay = 5000)
    public void refresh() {
        if(deferredResult != null) {
            deferredResult.setResult(new Long(System.currentTimeMillis()).toString());
        }
    }
}
