package lkp.an;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lz130
 * 网络编程概述：
 *  网络互连的不同计算机上运行的程序间进行数据交换
 * 网络模型：
 *  OSI(Open System Interconnection开放系统互连) 参考模型
 *  TCP/IP 参考模型
 *  OSI模型如下：                                                                                                    TCP/IP参考模型：
 *  应用层       （终端应用）                                                                                              应用层
 *  表示层       （对接收的数据进行加密解密，等）
 *  会话层       （通过传输层建立的数据传输通路。主要在系统之间发起会话或者接收会话请求）
 *  传输层       （定义一些传输数据的协议和端口号）                                                                         传输层
 *  网络层       （将下层接收到的数据进行IP地址的封装与解封，这一层的设备是路由器，这一层的数据叫做数据包）                     网际层
 *  数据链路层   （将物理层接收的数据进行mac地址的封装与解封，这一层的数据叫做帧。这一层的设备是交换机，数据通过交换机来传输）    主机至网络层
 *  物理层       （定义物理设备标准，网线接口类型光纤参数，传输速率等，主要作用是传输比特流，这一层的数据叫做比特）
 *
 * 网络编程三要素：
 *  IP，端口，协议
 *  ip地址：网络中计算机的唯一标识
 *  （点分十进制）
 *  （只识别二进制）但地址却不是二进制，因为以二进制数据进行操作比较麻烦，需要与10进制之间进行转换）
 *  ipconfig：查看本机IP地址
 *  ping ip地址：测试本机与指定的IP地址间的通信是否有问题
 *  特殊IP地址：127.0.0.1（回环地址，表是本机）
 *
 * 网络编程UDP
 *  将数据源和目的封装成数据包中，不需要建立连接；每个数据包的大小现在在64k；（无连接，不可靠，速度快）
 * 网络编程TCP
 *  建立连接，形成传输数据的通道，在连接中进行大数据量的传输；通过三次握手完成连接；（必须连接，可靠，效率低）
 * 应用：UDP发短信，TCP打电话
 * Socket套接字：
 * 网络上具有唯一标识的ip地址和端口号组合在一起才能构成唯一能识别的标识符套接字
 * Socket原理机制：
 * 通信的两端都有socket。
 * 网络通信其实就是Socket间的通信。
 * 数据在两个Socket间通过IO传输。
 *
 */
public class Socket1 {
    public static void main(String[] args) throws UnknownHostException {
        // method1
        //InetAddress inetAddress = InetAddress.getByName("DESKTOP-UJ7RF0N");
        InetAddress inetAddress = InetAddress.getByName("10.25.88.53");
        String name = inetAddress.getHostName();
        String ip = inetAddress.getHostAddress();
        System.out.println(name+"---"+ip);
        // method2

    }
}
