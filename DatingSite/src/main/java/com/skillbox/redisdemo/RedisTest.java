package com.skillbox.redisdemo;

import java.util.Random;

import static java.lang.System.out;

public class RedisTest {

    // Запуск докер-контейнера:
    // docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis

    // На сайте знакомств зарегистрировалось 20 пользователей
    private static final int COUNT = 20;

    // Также мы добавим задержку между посещениями
    private static final int SLEEP = 500; // 1 миллисекунда

    // В одном из 10 случаев случайный пользователь оплачивает услугу, в консоль выводится его номер
    private static final int randomUser = 10;

    private static void log(String User_id) {
        String log = String.format("— На главной странице показываем пользователя %s", User_id);
        out.println(log);
    }

    private static void log_1(String User_id) {
        String log = String.format("> Пользователь %s оплатил платную услугу", User_id);
        out.println(log);
    }
    public static void main(String[] args) throws InterruptedException {

        RedisStorage redis = new RedisStorage();
        redis.init();
        // Заполняем redis-server пользователями
        for(int i = 1; i <= COUNT; i++) {
            String user_id = String.valueOf(i);
            redis.saveUsers(user_id);
            Thread.sleep(SLEEP);
        }
        // Эмулируем вывод очереди в консоль 10 раз
        for(; ; ) {
            int i = 1;
            for (int numberInQueue = 1; numberInQueue <= COUNT; numberInQueue++) {
                String user_id;
                if (i == 10) {
                    int value;
                    do {
                        value = new Random().nextInt(COUNT);// Избавляемся от пользователя 0
                    } while (value == 0);
                    user_id = String.valueOf(value);
                    log_1(user_id);
                    i = 1;
                } else {
                    user_id = redis.getUsers();
                    i++;
                }

                log(user_id);
                redis.saveUsers(user_id);
                Thread.sleep(SLEEP);

            }
        }
    }
}
