package proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Берем сервера с сайта http://free-proxy.cz/ru/proxylist/country/RU/https/ping/level1
 * загружаем в текстовик и проверяем валидность тут https://free.proxy-sale.com/proxy-checker/ (Убираем нерабочие)
 */
public class ProxyClass {

    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("firewall.company.com", 80));
}
