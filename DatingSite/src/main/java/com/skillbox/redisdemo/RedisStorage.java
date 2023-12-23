package com.skillbox.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> queueOfUsers;

    private final static String KEY = "QUEUE_OF_USERS";

    private double getTs() {
        return new Date().getTime() / 1000;
    }


    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        queueOfUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void shutdown() {
        redisson.shutdown();
    }

    // Сохранение пользователя в очередь
    void saveUsers(String user_id) {

        //ZADD users in queue
        queueOfUsers.add(getTs(), user_id);
    }


    // Получение первого в очереди пользователя
    public String getUsers() {

        //ZRANGE QUEUE_OF_USERS 0 1
        return queueOfUsers.first();


    }

}
