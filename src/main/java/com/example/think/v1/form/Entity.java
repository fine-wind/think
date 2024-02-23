package com.example.think.v1.form;

import com.example.think.utils.ThreadPoolUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * 一个新新的个体
 * 个体本身宏观上是同步的
 * 不要求它是n会话的
 */
@Data
@Slf4j
@NoArgsConstructor
public class Entity implements Runnable, Serializable {
    /**
     * 启动信号位置
     */
    public static final Integer BOOT = 0;

    /**
     * 有很多细胞
     */
    private Map<Integer, Cell> cells;

    /**
     * 脑部的连接
     */
    public Map<Integer, Set<Link>> links;

    /**
     * 初始化脑图
     *
     * @param n    脑图边长
     * @param new_ .
     */
    public Entity(int n, boolean new_) {
        if (new_) {
            this.cells = new HashMap<>(n, 1);
            this.links = new HashMap<>(n, 1);
        }
        for (int i = 0; i < n; i++) {
            this.cells.put(i, new Cell(i));
        }
        this.links = new HashMap<>(n, 1);
    }

    /**
     * 脑子是个好东西, 用它干点什么呢？
     * todo 道生一，一生二 这怎么生的三，是由冲突导致
     * todo 应该让其思考
     */
    public void init() {
        if (Objects.nonNull(this.links.get(BOOT))) {
            return;
        }
        // todo 进行初始化操作
        HashSet<Link> value = new HashSet<>();
        this.links.put(BOOT, value);
        // 随机一些细胞，填充
        this.cells.keySet().forEach(cellId -> {
            Set<Link> linkSet = this.links.getOrDefault(cellId, new HashSet<>());
            linkSet.add(Link.random(cells.size()));
            this.links.put(cellId, linkSet);
        });
    }

    /**
     * 这里接收输入的信号
     *
     * @param in 输入的信息
     */
    public static String connect(String... in) {
        if (Objects.isNull(in) || in.length == 0) {
            return null;
        }

        // todo 将信号扔到脑子中进行处理
        // 怎么处理信号是个大问题!!
        String aa = "aa";
        log.debug("think: {}", aa);
        return aa;
    }

    /**
     * 如果没有任何信号 就需要干点什么， 没刺激的系统像一滩死水
     */
    @Override
    public void run() {
        // 进行思考
        Set<Link> boot0 = this.links.get(BOOT);
        /* 细胞任务列表*/
        List<Integer> taskList = new LinkedList<>(boot0.stream().map(Link::getCellId).toList());
        //noinspection InfiniteLoopStatement
        while (true) {
            /* 负责处理计算方面*/
            Integer e;
            try {
                e = taskList.remove(0);
            } catch (NoSuchElementException exception) {
                ThreadPoolUtil.sleep(1000);
                continue;
            }
            ThreadPoolUtil.submitScheduled(this.js(e, taskList));
            // 处理指数级连接问题！！！
            if (taskList.isEmpty() || taskList.size() > 1000) {
                ThreadPoolUtil.sleep();
            }
        }
    }

    /**
     * 细胞之间的计算
     * todo 怎样动态处理细胞之间的连接性
     *
     * @param taskListTemp 临时存储的待计算
     * @return 任务
     */
    private Runnable js(Integer cellId, List<Integer> taskListTemp) {
        return () -> {
            Cell cell = this.cells.get(cellId);
            // log.debug("当前细胞编号 {} 线量 {}, 计算队列 {}", cellId, cell.getXl(), taskListTemp.size());
            Map<Integer, Message> run = cell.run(this.links.get(cellId));
            run.forEach((k, v) -> this.getCells().get(k).input(v));
            taskListTemp.addAll(run.keySet());
        };
    }
}
