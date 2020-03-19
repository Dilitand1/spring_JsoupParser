package proxy;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *Нужно найти перечень прокси серверов и сохранить в файл proxy.txt
 *
 */
public class ProxyFactory {

    public static Queue<Proxy> getProxy(String path){
        ConcurrentLinkedQueue<Proxy> proxyQueue = new ConcurrentLinkedQueue<>();
        proxyQueue.offer(Proxy.NO_PROXY);
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                proxyQueue.offer(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(line.split(";")[0]
                        ,new Integer(line.split(";")[1]))));
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return proxyQueue;
    }
    public static ProxyFactory getProxy2(){
        return null;
    }
}
