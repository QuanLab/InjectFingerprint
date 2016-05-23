package com.vccorp.bigdata.LRUCache;

import java.util.HashMap;

/**
 * Created by quanpv on 5/12/16.
 */

public class LRUCache {

    int capacity;
    HashMap<String, UserAgent> map = new HashMap<String, UserAgent>();
    UserAgent head=null;
    UserAgent end=null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public String get(String key) {
        if(map.containsKey(key)){
            UserAgent n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }

        return null;
    }

    public void remove(UserAgent n){
        if(n.pre!=null){
            n.pre.next = n.next;
        }else{
            head = n.next;
        }

        if(n.next!=null){
            n.next.pre = n.pre;
        }else{
            end = n.pre;
        }

    }

    public void setHead(UserAgent n){
        n.next = head;
        n.pre = null;

        if(head!=null)
            head.pre = n;

        head = n;

        if(end ==null)
            end = head;
    }

    public void set(String key, String value) {
        if(map.containsKey(key)){
            UserAgent old = map.get(key);
            old.value = value;
            remove(old);
            setHead(old);
        }else{
            UserAgent created = new UserAgent(key, value);
            if(map.size()>=capacity){
                map.remove(end.key);
                remove(end);
                setHead(created);

            }else{
                setHead(created);
            }

            map.put(key, created);
        }
    }
}