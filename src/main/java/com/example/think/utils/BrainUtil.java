package com.example.think.utils;

import com.example.think.v0.config.Configs;
import com.example.think.v1.form.Entity;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class BrainUtil {

    final static String directoryPath = System.getProperty("user.dir") + "/brain/";
    /**
     * 脑文件的后缀
     */
    final static String bj = ".brain.json";

    /**
     * 记录脑子情况
     *
     * @param entity 脑
     * @return 任务
     */
    public static Runnable saveBrain(final Entity entity) {
        return () -> {
            long l1 = System.currentTimeMillis();

            String filePath = directoryPath + DateUtil.nowStr() + bj;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
               // todo writer.write(JSON.toJSONString(entity));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long arg = System.currentTimeMillis() - l1;
            log.debug("记录一次脑快照 耗时{}", arg < 1000 ? arg + "ms" : (arg / 1000 + "s") + (arg % 1000 + "ms"));
        };
    }

    /**
     * 加载本地脑
     *
     * @return .
     */
    public static Entity load() {
        File folder = new File(directoryPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                log.info("仓库初始化成功");
            }
        }
        Entity entity;
        if (!folder.isDirectory()) {
            return null;
        }
        File[] files = folder.listFiles();
        assert files != null;
        List<File> list = new ArrayList<>(Arrays.stream(files)
                .filter(File::isFile)
                .filter(e -> e.getName().endsWith(bj))
                .filter(e -> e.length() > 0)
                // .sorted(Comparator.comparingLong(File::lastModified))
                .sorted(Comparator.comparing(File::getName))
                .toList());
        Collections.reverse(list);
        log.info("发现 {}个 本地模型", list.size());
        if (list.isEmpty()) {
            log.info("将自动根据配置分化");
            return new Entity(Configs.CALL_NUMBER, true);
        }

        log.info("列表：");
        for (int _i = 0; _i < list.size(); _i++) {
            log.info("\t{}: {}", _i, list.get(_i).getName());
        }
        File file = list.get(0);
        log.info("自动选择第一个：{}", file.getName());
        /* 初始化实体*/
        entity = read(file);
        return entity;
    }

    private static Entity read(File file) {
        log.info("正在加载数据...");
        if (file.canRead()) {
            String s;
            try {
                s = Files.readString(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                log.error("出现错误");
                throw new RuntimeException(e);
            }
            Entity entity = null;// todo JSON.parseObject(s, Entity.class);
            log.info("数据加载加载完毕");
            return entity;
        }
        return null;
    }

    /**
     * 日志
     *
     * @param entity 脑
     * @return 线程
     */
    public static Runnable log(final Entity entity) {
        return () -> log.debug("总线程 {} 总细胞 {}", Thread.activeCount(), entity.getCells().size());
    }
}
