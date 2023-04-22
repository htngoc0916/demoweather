package com.example.weather.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
public class HandleTask {
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void initServer(){
        appendTask("com.example.weather.task.WeatherTask");
    }

    public boolean appendTask(String pkgNm) {
        boolean result = false;
        try {
            Runnable r = getRunnable(pkgNm);

            //등록 시 바로 TASK가 실행되는 관계로 PERIOD 만큼 시작시간을 늦춰서 등록 추가
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(new Date());
            startTime.add(Calendar.SECOND, 2);  // 2초 후에 시작

            ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleWithFixedDelay(r, startTime.getTime(), 1 * 1000);   // 1초에 한번씩 호출
            Thread.sleep(1000);
            result = true;
        } catch(BeansException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Runnable getRunnable(String pkgNm) throws BeansException, ClassNotFoundException {
        try {
            ArchiveTask task = (ArchiveTask)context.getBean(Class.forName(pkgNm));
            return new Runnable() {
                @Override
                public void run() {
                    task.runTask();
                }
            };
        } catch(BeansException | ClassNotFoundException e) {
            throw e;
        }
    }
}
