package com.example.think.utils;

import com.alibaba.fastjson2.JSON;
import com.example.think.config.Configs;
import com.example.think.v1.form.Entity;
import com.example.think.v1.form.Link;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
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
            //noinspection InfiniteLoopStatement
            while (true) {
                ThreadPoolUtil.sleep(1000 * 60 * 5);// 5分钟
                long l1 = System.currentTimeMillis();
                String filePath = directoryPath + DateUtil.nowStr() + bj;
                // 序列化对象到文件
                String jsonString = JSON.toJSONString(entity);
                File fileNamePattern = new File(filePath);
                try (FileWriter fw = new FileWriter(fileNamePattern, Charset.defaultCharset())) {
                    fw.write(jsonString);
                } catch (IOException e) {
                    log.error("记录快照失败", e);
                }
                log.debug("记录一次脑快照 耗时 {}", DateUtil.time(System.currentTimeMillis() - l1));
            }
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

        if (!folder.isDirectory() && folder.delete()) {
            log.info("删除不合规文件");
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
        return read(file);
    }

    private static Entity read(File file) {
        log.info("正在加载数据...");
        long l = System.currentTimeMillis();
        if (file.canRead()) {
            String path = file.getAbsolutePath();
            Entity entity;

            // 反序列化对象
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                entity = JSON.parseObject(ois, Entity.class);
            } catch (IOException e) {
                log.error("加载 {} 出现错误", path, e);
                throw new RuntimeException(e);
            }
            log.info("数据加载加载完毕 {}", DateUtil.time(System.currentTimeMillis() - l));
            return entity;
        }
        throw new RuntimeException("无法读取模型文件");
    }

    /**
     * 日志
     *
     * @param entity 脑
     * @return 线程
     */
    public static Runnable log(final Entity entity) {
        return () -> {
            //noinspection InfiniteLoopStatement
            while (true) {
                log.debug("总线程 {} 总细胞 {}", Thread.activeCount(), entity.getCells().size());
                ThreadPoolUtil.sleep(5000);
            }
        };
    }

    /**
     * todo 检查个体细胞链接问题，避免出现分离
     *
     * @param entity 个体
     */
    public static void checkBrainLink(Entity entity) {
        /* 所有的链接信息*/
        Set<Integer> integers = new HashSet<>(entity.getLinks().keySet());
        removeLink(entity, Entity.BOOT, integers);
        for (Integer integer : integers) {
            entity.getLinks().remove(integer);
        }
    }

    private static void removeLink(Entity entity, Integer cellId, Set<Integer> cellIds) {
        for (Link link : entity.getLinks().get(cellId)) {
            removeLink(entity, link.getCellId(), cellIds);
            cellIds.remove(link.getCellId());
        }
    }
}
