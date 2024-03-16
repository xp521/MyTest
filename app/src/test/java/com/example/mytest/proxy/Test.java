package com.example.mytest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/7/12.
 */
public class Test {
    public static void main(String[] args) {
        IPerson person = new CPerson();
        IPerson proxy = (IPerson) new InvocationProxy(person).getProxy();
        proxy.get();
    }

    public static class ProxyPerson implements IPerson {

        public IPerson target;
        public ProxyPerson(IPerson target) {
            this.target = target;
        }

        @Override
        public void get() {
            System.out.println("before");
            target.get();
            System.out.println("after");
        }
    }

    public static class InvocationProxy implements InvocationHandler {
        private IPerson target;
        public InvocationProxy(IPerson target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //System.out.println(String.valueOf(proxy));
            System.out.println("before1");
            Object object =  method.invoke(target, args);
            System.out.println("after1");

            return object;
        }

        // 生成代理对象
        public Object getProxy() {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class[] interfaces = target.getClass().getInterfaces();
            return Proxy.newProxyInstance(loader, interfaces, this);
        }
    }
}
