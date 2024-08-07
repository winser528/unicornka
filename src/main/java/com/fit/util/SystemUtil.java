package com.fit.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @AUTO 系统信息工具类
 * @DATE 2019/4/19
 */
public class SystemUtil {

    // 操作系统的版本
    public static final String OS_VERSION = System.getProperty("os.version", null);
    // 操作系统的名称
    public static final String OS_NAME = System.getProperty("os.name", null);
    // 处理器架构
    public static final String OS_ARCH = System.getProperty("os.arch", null);
    // 用户的当前工作目录
    public static final String USER_DIR = System.getProperty("user.home", null);
    // 当前操作系统用户名
    public static final String USER_NAME = System.getProperty("user.name", null);
    // 项目部署路径
    public static final String ITEM_PATH = System.getProperty("user.dir", null);
    // 系统文件分隔符
    public static final String FILE_SEPARATOR = System.getProperty("file.separator", "/");
    // 系统换行符
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\\n");
    // 系统路径分隔符
    public static final String PATH_SEPARATOR = System.getProperty("path.separator", null);
    // JAVA版本
    public static final String JAVA_VERSION = System.getProperty("java.version", null);
    //
    public static final String COMPUTER_NAME = System.getenv().get("COMPUTERNAME");
    // 当前系统下的临时文件路径
    public static final String TEMP_PATH = System.getenv().get("java.io.tmpdir");

    /**
     * int
     * 取得当前系统的信息
     */
    public static Map<String, String> getOsInfo() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        try {
            map.put("ip", getIp());//获取本机ip
            map.put("mac", getLocalMac());//获取本机ip
            map.put("hostName", ConverterUtils.toString(InetAddress.getLocalHost().getHostName()));//获取本机计算机名称
            map.put("osname", OS_NAME);
            map.put("version", OS_VERSION);
            map.put("arch", OS_ARCH);
            map.put("sysTime", DateUtils.getNowTime());
            map.put("userdir", USER_DIR);
            map.put("sysUserName", USER_NAME);
            map.put("computerName", ConverterUtils.toString(COMPUTER_NAME, "administrator"));
            map.put("itemPath", ITEM_PATH);
            map.put("vmRamTotal", Runtime.getRuntime().totalMemory() / 1024L / 1024L + " M");
            map.put("useRamTotal", Runtime.getRuntime().maxMemory() / 1024L / 1024L + " M");
            map.put("java", JAVA_VERSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取网卡，获取地址
     */
    public static String getLocalMac() {
        StringBuffer sb = new StringBuffer();
        try {
            InetAddress ia = getLocalhost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 获取本机访问地址
     */
    public static String getIp() {
        return ConverterUtils.toString(getLocalhost().getHostAddress());
    }

    private static boolean isVM(String name) {
        String displayName = name.toUpperCase();
        if (displayName.contains("#") || displayName.contains("Virtual".toUpperCase()) || displayName.contains("VMnet".toUpperCase())) {
            return true;
        }
        return false;
    }

    /**
     * 获取本机网卡IP地址，这个地址为所有网卡中非回路地址的第一个<br>
     * 如果获取失败调用 {@link InetAddress#getLocalHost()}方法获取。<br>
     * 此方法不会抛出异常，获取失败将返回<code>null</code><br>
     *
     * @return 本机网卡IP地址，获取失败返回<code>null</code>
     */
    private static InetAddress getLocalhost() {
        InetAddress candidateAddress = null;
        NetworkInterface iface;
        try {
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                iface = ifaces.nextElement();
                String name = iface.getDisplayName();
                if (isVM(name)) {
                    continue;
                }
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (false == inetAddr.isLoopbackAddress()) {// 是否是一个loopback(回送地址)
                        if (inetAddr.isSiteLocalAddress()) {// 是否是地区本地地址
                            return inetAddr;
                        } else if (null == candidateAddress) {
                            // 非site-local地址做为候选地址返回
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (null == candidateAddress) {
                candidateAddress = InetAddress.getLocalHost();
            }
        } catch (SocketException | UnknownHostException e) {
        }
        return candidateAddress;
    }

    /**
     * 取得当前项目路径（取自系统属性：<code>os.arch</code>）。
     *
     * <p>
     * 例如：<code>"C:\WINDOWS\system32"</code>
     * </p>
     *
     * @return 属性值，如果不能取得或值不存在，则返回<code>null</code>。
     */
    public static final String getItemPath() {
        return ITEM_PATH;
    }

    /**
     * 取得当前OS的架构（取自系统属性：<code>os.arch</code>）。
     *
     * <p>
     * 例如：<code>"x86"</code>
     * </p>
     *
     * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
     */
    public static final String getArch() {
        return OS_ARCH;
    }

    /**
     * 取得当前OS的名称（取自系统属性：<code>os.name</code>）。
     *
     * <p>
     * 例如：<code>"Windows XP"</code>
     * </p>
     *
     * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
     */
    public static final String getOsName() {
        return OS_NAME;
    }

    /**
     * 取得当前OS的版本（取自系统属性：<code>os.version</code>）。
     *
     * <p>
     * 例如：<code>"5.1"</code>
     * </p>
     *
     * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
     */
    public static final String getVersion() {
        return OS_VERSION;
    }

    /**
     * 取得OS的文件路径的分隔符（取自系统属性：<code>file.separator</code>）。
     *
     * <p>
     * 例如：Unix为<code>"/"</code>，Windows为<code>"\\"</code>。
     * </p>
     *
     * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
     */
    public static final String getFileSeparator() {
        return FILE_SEPARATOR;
    }

    /**
     * 取得OS的文本文件换行符（取自系统属性：<code>line.separator</code>）。
     *
     * <p>
     * 例如：Unix为<code>"\n"</code>，Windows为<code>"\r\n"</code>。
     * </p>
     *
     * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
     */
    public static final String getLineSeparator() {
        return LINE_SEPARATOR;
    }

    /**
     * 取得OS的搜索路径分隔符（取自系统属性：<code>path.separator</code>）。
     *
     * <p>
     * 例如：Unix为<code>":"</code>，Windows为<code>";"</code>。
     * </p>
     *
     * @return 属性值，如果不能取得（因为Java安全限制）或值不存在，则返回<code>null</code>。
     */
    public static final String getPathSeparator() {
        return PATH_SEPARATOR;
    }
}
