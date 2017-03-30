package com.search2.protal.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class ZkCreatNode {
    private static ZooKeeper zk;
    private static ZooKeeperConnection conn;

    public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public static void main(String[] args) {
        String path = "/MyFirstZnode";
        byte[] data = "My first zookeeper app".getBytes();
        try {
            conn = new ZooKeeperConnection();
            zk = conn.connect("192.168.222.118:2181,192.168.222.118:2182,192.168.222.118:2183");
            create(path, data);
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
