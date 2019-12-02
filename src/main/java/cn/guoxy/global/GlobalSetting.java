package cn.guoxy.global;

import cn.guoxy.model.Member;
import cn.hutool.setting.dialect.Props;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * @author GuoXiaoyong
 */
public class GlobalSetting {
    private static GlobalSetting instance;
    private Set<Member> members;
    private Props props;

    private GlobalSetting() {
        members = new HashSet<>();
        props = new Props("application.properties");
    }

    public static GlobalSetting getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GlobalSetting.class) {
                if (Objects.isNull(instance)) {
                    instance = new GlobalSetting();
                }
            }
        }
        return instance;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void addMember(Member member) {
        synchronized (this) {
            if (!this.members.contains(member)) {
                this.members.add(member);
            }
        }
    }

    public void addMembers(Set<Member> members) {
        synchronized (this) {
                this.members.addAll(members);
        }
    }

    public List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    public void removeMember(Member member) {
        synchronized (this) {
            this.members.remove(member);
        }
    }


    public Props getProps() {
        return props;
    }
}
