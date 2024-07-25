package com.fit.config;

/**
 * @AUTO 雪花算法
 * @Author AIM
 * @DATE 2024/7/25
 */
public class SnowFlake {
    /**
     * 起始的时间戳 '2024-01-01 00:00:00'
     */
    private final static long START_CTM = 1704038400000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastCTM = -1L;//上一次时间戳

    /**
     * 配置数据中心ID和机器ID
     *
     * @param datacenterId 数据中心ID
     * @param machineId    机器标识ID
     */
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public String nextSn() {
        return Long.toString(nextId());
    }

    /**
     * 产生下一个ID
     */
    public synchronized long nextId() {
        long now = getCTM(); // 当前系统时间戳
        if (now < lastCTM) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        } else if (now == lastCTM) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                now = getNextMill();
            }
        } else { //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastCTM = now;

        return (now - START_CTM) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT  //数据中心部分
                | machineId << MACHINE_LEFT        //机器标识部分
                | sequence;                        //序列号部分
    }

    private long getNextMill() {
        long mill = getCTM();
        while (mill <= lastCTM) {
            mill = getCTM();
        }
        return mill;
    }

    /**
     * 系统毫秒时间
     */
    private long getCTM() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }
    }
}
