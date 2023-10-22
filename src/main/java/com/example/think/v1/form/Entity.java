package com.example.think.v1.form;

import com.example.think.utils.ThreadPoolUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 一个新新的个体
 * 个体本身宏观上是同步的
 */
@Data
@Slf4j
public class Entity implements Runnable {
    /**
     * 启动信号位置
     */
    private static final Integer boot = 0;

    /**
     * 有很多细胞
     */
    private HashMap<Integer, Cell> cells;

    /**
     * 脑部的连接
     */
    public HashMap<Integer, Set<Link>> links;

    /**/
    private boolean change = false;

    public Entity() {

    }

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
        if (Objects.nonNull(this.links.get(boot))) {
            return;
        }
        // todo 进行初始化操作
        HashSet<Link> value = new HashSet<>();
        this.links.put(boot, value);
        // 随机一些细胞，填充
        value.add(Link.random(cells.size()));
        this.cells.keySet().forEach(cellId -> {
            Set<Link> linkSet = this.links.getOrDefault(cellId, new HashSet<>());
            linkSet.add(Link.random(cells.size()));
            this.links.put(cellId, linkSet);
        });
        this.setChange(true);
    }

    /**
     * 这里接收输入的信号
     *
     * @param in 输入的信息
     */
    public void connect(String in) {
        if (in.isEmpty()) {
            return;
        }

        // todo 将信号扔到脑子中进行处理
        // 怎么处理信号是个大问题!!
        this.out();
    }

    /**
     * 输出模块
     *
     * @return 输出结果
     */
    public String out() {
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
        Set<Link> boot0 = this.links.get(boot);
        /* 细胞任务列表*/
        LinkedList<Integer> taskList = new LinkedList<>(boot0.stream().map(Link::getCellId).toList());
        while (!taskList.isEmpty()) {
            /* 负责处理计算方面*/
            Integer e;
            try {
                e = taskList.removeFirst();
            } catch (NoSuchElementException exception) {
                continue;
            }
            ThreadPoolUtil.submitScheduled(this.js(e, taskList));
            // 处理指数级连接问题！！！
            if (taskList.isEmpty() || taskList.size() > 1000) {
                ThreadPoolUtil.sleep();
            }
            this.setChange(true);
        }
    }

    /**
     * 细胞之间的计算
     * todo 怎样动态处理细胞之间的连接性
     *
     * @param taskListTemp 临时存储的待计算
     * @return 任务
     */
    private Runnable js(Integer cellId, LinkedList<Integer> taskListTemp) {
        return () -> {
            Cell cell = this.cells.get(cellId);
            log.debug("当前细胞编号 {} 线量 {}, 计算队列 {}", cellId, cell.getXl(), taskListTemp.size());
            Map<Integer, Message> run = cell.run(this.links.get(cellId));
            run.forEach((k, v) -> this.getCells().get(k).input(v));
            taskListTemp.addAll(run.keySet());
        };
    }
}
