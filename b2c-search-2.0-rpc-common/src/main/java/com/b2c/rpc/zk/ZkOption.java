package com.b2c.rpc.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.b2c.rpc.exception.ZkConnectionException;
import com.b2c.rpc.util.Constant;

public class ZkOption {
    private static volatile List<String> dataList = new ArrayList<String>();
    private static ZooKeeper zk;
    private static ZooKeeperConnection conn;

    public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public static void create(String path, byte[] data, String hosts) {
        try {
            conn = new ZooKeeperConnection();
            zk = conn.connect(hosts);
            if(zk.exists(path, true) != null){
                zk.setData(path, data, zk.exists(path, true).getVersion());
            } else {
                create(path, data);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String discover(String hosts) throws Exception {
        try {
            conn = new ZooKeeperConnection();
            zk = conn.connect(hosts);
            if (zk != null) {
                watchNode(zk);
            }
            String data = null;
            int size = dataList.size();
            if (size > 0) {
                if (size == 1) {
                    data = dataList.get(0);
                } else {
                    data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                }
            }
            return data;
        } catch (IOException | InterruptedException e) {
           throw new ZkConnectionException("ZK Connection is fail");
        } finally{
            try {
                conn.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            for (String node : nodeList) {
                byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
