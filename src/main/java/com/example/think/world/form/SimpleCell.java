package com.example.think.world.form;

import com.example.think.utils.ThreadPoolUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 简简单单的一个细胞
 */
@Slf4j
public class SimpleCell implements Runnable {

    @Getter
    private final String id;
    /**
     * 每个细胞都可能会连接上多个下级
     */
    private final Set<Link> links = new HashSet<>();
    /**
     * 传递给this的信号集合
     */
    private final LinkedList<Signal> signals = new LinkedList<>();


    public SimpleCell(String id) {
        this.id = id;
    }

    /**
     * 连接
     *
     * @param cell cell
     */
    public void link(Collection<SimpleCell> cell) {
        this.links.add(new Link().link(this, cell));
    }

    /**
     * 树突是从细胞体伸出的短突，主要用于接收其他神经元的信号。
     * 没有输入会产生循环的情况
     *
     * @param signal 信号量
     */
    @SneakyThrows
    public void input(Signal signal) {
        log.debug("{} 细胞 接收到信号{} 信号队列{} 下级{}", getId(), signal, this.signals.size(), this.links.size());
        Thread.sleep(this.signals.size() * 100L);// 避免宕机
        if (Objects.isNull(signal)) {
            // 如果没有传递任何信号 就需要想办法干点什么， 没刺激的系统像一滩死水
            signal = new Signal().setSignal(0);
        }
        this.signals.add(signal);
        ThreadPoolUtil.scheduleAtFixedRate(this);
    }

    /**
     * 思考
     */
    @Override
    public void run() {
        // 处理信号并传递给下一个神经元
        links.parallelStream().forEach(link -> link.next(this.signals.remove().setCellId(getId())));
    }

}
