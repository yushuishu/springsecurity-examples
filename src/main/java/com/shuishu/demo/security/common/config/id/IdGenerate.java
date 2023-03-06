package com.shuishu.demo.security.common.config.id;


import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 19:52
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public final class IdGenerate {
    // ==============================Fields===========================================
    /**
     * 开始时间截 (2019-01-01)
     */
    private final long twepoch = 1546272000000L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 8L;

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 毫秒级别时间截占的位数
     */
    private final long timestampBits = 41L;

    /**
     * 生成发布方式所占的位数
     */
    private final long getMethodBits = 2L;

    /**
     * 支持的最大机器id，结果是255 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 生成序列向左移8位(8)
     */
    private final long sequenceShift = workerIdBits;

    /**
     * 时间截向左移20位(12+8)
     */
    private final long timestampShift = sequenceBits + workerIdBits;

    /**
     * 生成发布方式向左移61位(41+12+8)
     */
    private final long getMethodShift = timestampBits + sequenceBits + workerIdBits;

    /**
     * 工作机器ID(0~255)
     */
    private long workerId = 0L;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 2位生成发布方式，0代表嵌入式发布、1代表中心服务器发布模式、2代表rest发布方式、3代表保留未用
     */
    private long getMethod = 0L;

    /**
     * 成发布方式的掩码，这里为3 (0b11=0x3=3)
     */
    private long maxGetMethod = -1L ^ (-1L << getMethodBits);
    /**
     * 重入锁
     */
    private Lock lock = new ReentrantLock();

    //==============================Constructors=====================================

    /**
     * 构造函数
     *
     * @param getMethod 发布方式 0代表嵌入式发布、1代表中心服务器发布模式、2代表rest发布方式、3代表保留未用 (0~3)
     * @param workerId  工作ID (0~255)
     */
    public IdGenerate(long getMethod, long workerId) {
        if (getMethod > maxGetMethod || getMethod < 0) {
            throw new IllegalArgumentException(format("getMethod can't be greater than %d or less than 0", maxGetMethod));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.getMethod = getMethod;
        this.workerId = workerId;
    }

    public long[] nextId(int nums) {
        long[] ids = new long[nums];
        for (int i = 0; i < nums; i++) {
            ids[i] = nextId();
        }
        return ids;
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public long nextId() {
        return nextId(workerId);
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId(long workerId) {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            lock.lock();
            try {
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } finally {
                lock.unlock();
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

//        long Method1 = (getMethod << getMethodShift);
//        long time1 = ((timestamp - twepoch) << timestampShift);
//        long sequence1 = (sequence << sequenceShift);
//
//        System.out.println(format("(getMethod << getMethodShift)：(%s << %s) = %s", getMethod, getMethodShift, Method1));
//        System.out.println(format("((timestamp - twepoch) << timestampShift)：((%s - %s) << %s) = %s", timestamp, twepoch, timestampShift, time1));
//        System.out.println(format("(sequence << sequenceShift)：(%s << %s) = %s", sequence, sequenceShift, sequence1));
//        System.out.println("workerId：" + workerId);
//
//        long step0 = (getMethod << getMethodShift);
//        long step1 = ((timestamp - twepoch) << timestampShift);
//        long step2 = (sequence << sequenceShift);
//        long step3 = workerId;
//        long step4 = (getMethod << getMethodShift) | ((timestamp - twepoch) << timestampShift) | (sequence << sequenceShift) | workerId;
//
//        System.out.println("sep0：" + Long.toBinaryString(Long.parseLong(step0 + "")));
//        System.out.println("sep1：" + Long.toBinaryString(Long.parseLong(step1 + "")));
//        System.out.println("sep2：" + Long.toBinaryString(Long.parseLong(step2 + "")));
//        System.out.println("sep3：" + Long.toBinaryString(Long.parseLong(step3 + "")));
//        System.out.println("sep4：" + Long.toBinaryString(Long.parseLong(step4 + "")));

        //移位并通过或运算拼到一起组成64位的ID
        return (getMethod << getMethodShift) // 生成方式占用2位，左移61位
                | ((timestamp - twepoch) << timestampShift) // 时间差占用41位，最多69年，左移20位
                | (sequence << sequenceShift) // 毫秒内序列，取值范围0-4095
                | workerId; // 工作机器，取值范围0-255
    }

    /**
     * 此法不能用来添加数据 时生产id, 只能用来需要按时间查询的数据，生产id后再查询
     *
     * @param timestamp
     * @return
     */
    public long nextIdWithTimestamp(long timestamp) {
        //移位并通过或运算拼到一起组成64位的ID
        return (getMethod << getMethodShift) // 生成方式占用2位，左移61位
                | ((timestamp - twepoch) << timestampShift) // 时间差占用41位，最多69年，左移20位
                | (sequence << sequenceShift) // 毫秒内序列，取值范围0-4095
                | workerId; // 工作机器，取值范围0-255
    }

    public String nextString() {
        return Long.toString(nextId());
    }

    public String[] nextString(int nums) {
        String[] ids = new String[nums];
        for (int i = 0; i < nums; i++) {
            ids[i] = nextString();
        }
        return ids;
    }

    public String nextHexString() {
        return Long.toHexString(nextId());
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    public JSONObject parseInfo(Long id) {

        String idStr = Long.toBinaryString(id.longValue());
        int len = idStr.length();
        JSONObject jsonObject = new JSONObject();

        //移位并通过或运算拼到一起组成64位的ID
//        return  (getMethod << getMethodShift) // 生成方式占用2位，左移61位
//                | ((timestamp - twepoch) << timestampShift) // 时间差占用41位，最多69年，左移20位
//                | (sequence << sequenceShift) // 毫秒内序列，取值范围0-4095
//                | workerId; // 工作机器，取值范围0-255

        int workerStart = len < sequenceShift ? 0 : (int) (len - sequenceShift);
        int sequenceStart = len < timestampShift ? 0 : (int) (len - timestampShift);
        int timeStart = len < getMethodShift ? 0 : (int) (len - getMethodShift);

        String workerId = idStr.substring(workerStart, len);
        int workerIdInt = Integer.valueOf(workerId, 2);
        jsonObject.put("workerId", workerIdInt);

        String sequence = workerStart == 0 ? "0" : idStr.substring(sequenceStart, workerStart);
        int sequenceInt = Integer.valueOf(sequence, 2);
        jsonObject.put("sequence", sequenceInt);

        String time = idStr.substring(timeStart, sequenceStart);
        long diffTime = Long.parseLong(time, 2);
        long timeLong = diffTime + twepoch;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(timeLong);
        jsonObject.put("date", date);

        String method = timeStart == 0 ? "0" : idStr.substring(0, timeStart);
        int methodInt = Integer.valueOf(method, 2);
        jsonObject.put("method", methodInt);

        return jsonObject;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }


    //==============================Test=============================================

    /**
     * 测试
     */

    public static void main(String[] args) {
        IdGenerate idGenerate = new IdGenerate(1, 0);

        // 测试ID解析
        for (int i = 0; i < 1000; i++) {
            long tempId = idGenerate.nextId();
            String tempId1 = Long.toBinaryString(tempId);

//            System.out.println(tempId);
//            System.out.println(tempId1);
//            System.out.println(idGenerate.parseInfo(tempId));
        }

        // 测试生成器性能
//        int count = 100000;//线程数=count*count
//        final long[][] times = new long[count][100];
//
//        Thread[] threads = new Thread[count];
//        for (int i = 0; i < threads.length; i++) {
//            final int ip = i;
//            threads[i] = new Thread() {
//                @Override
//                public void run() {
//                    for (int j = 0; j <100; j++) {
//                        long t1 = System.nanoTime();//该函数是返回纳秒的。1毫秒=1纳秒*1000000
//
//                        idGenerate.nextId();//测试
//
//                        long t = System.nanoTime() - t1;
//
//                        times[ip][j] = t;//求平均
//                    }
//                }
//
//            };
//        }
//
//        long lastMilis = System.currentTimeMillis();
//        //逐个启动线程
//        for (int i = 0; i < threads.length; i++) {
//            threads[i].start();
//        }
//
//        for (int i = 0; i < threads.length; i++) {
//            try {
//                threads[i].join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        /**
//         * 1、QPS：系统每秒处理的请求数（query per second）
//         2、RT：系统的响应时间，一个请求的响应时间，也可以是一段时间的平均值
//         3、最佳线程数量：刚好消耗完服务器瓶颈资源的临界线程数
//         对于单线程：QPS=1000/RT
//         对于多线程：QPS=1000*线程数量/RT
//         */
//        long time = System.currentTimeMillis() - lastMilis;
//        System.out
//                .println("QPS: "
//                        + (1000*count /time));
//
//        long sum = 0;
//        long max = 0;
//        for (int i = 0; i < times.length; i++) {
//            for (int j = 0; j < times[i].length; j++) {
//                sum += times[i][j];
//
//                if (times[i][j] > max)
//                    max = times[i][j];
//            }
//        }
//        System.out.println("Sum(ms)"+time);
//        System.out.println("AVG(ms): " + sum / 1000000 / (count*100));
//        System.out.println("MAX(ms): " + max / 1000000);
    }
}
