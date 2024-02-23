package com.example.think.v1.form;

import com.example.think.utils.RandomUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

/**
 * 简简单单的一个细胞
 * 每个细胞都可能会连接上多个下级
 */
@Accessors(chain = true)
public class Cell implements Serializable {
    /**
     * 细胞标识
     */
    @Getter
    private final int cellId;
    /**
     * 线性处理量
     * 指的是线性处理信息的长度
     */
    @Getter
    @Setter
    private int xl = 5;

    /**
     * 传递给this的信号集合
     */
    private final LinkedList<Message> signals = new LinkedList<>();

    public Cell(int cellId) {
        this.cellId = cellId;
        this.signals.add(new Message());
    }


    /**
     * 思考
     * 此处是细胞计算逻辑
     *
     * @param links 此细胞的下级连接
     * @return 返回此细胞计算后的一些东西
     */
    public Map<Integer, Message> run(Set<Link> links) {
        HashMap<Integer, Message> nm = new HashMap<>(links.size(), 1);

        Message first = this.signals.getFirst();
        if (Objects.nonNull(first)) {
            // todo 处理信号并传递给下一个神经元
            // 多个消息可能重叠从而引发关联性思维
            for (int i = 0; i < xl && i < this.signals.size(); i++) {
                Message message = this.signals.get(i);
            }
            links.forEach(link -> {
                Double jm = link.getJm();
                nm.put(link.getCellId(), new Message());
            });
        }
        return nm;
    }

    /**
     * todo 输入信号
     * 树突是从细胞体伸出的短突，主要用于接收其他神经元的信号。
     * 没有输入会产生循环的情况
     *
     * @param signal 信号量
     */
    public void input(Message signal) {
        // log.trace("{} 细胞 接收到信号 {} 信号队列{} 下级{}", getId(), signal, this.signals.size(), this.links.size());
        if (Objects.isNull(signal)) {
            if (RandomUtil.nextBoolean()) {
                return;
            }
        }
        this.signals.add(signal);
        // this.run(this.getLinks().get(e.getCellId()));
    }
}
