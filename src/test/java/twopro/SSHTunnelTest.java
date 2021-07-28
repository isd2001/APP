package twopro;

import com.jcraft.jsch.*;

import java.io.InputStream;

public class SSHTunnelTest {

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {

        SSHTunnelTest t = new SSHTunnelTest();
        try {
            t.go();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void go() throws Exception {

        StringBuilder outputBuffer = new StringBuilder();

        String host = "211.62.106.225";
        String user = "hansol";
        String password = "Digit2020!";
        String tunnelRemoteHost = "202.89.125.49"; // The host of the second target
        String secondPassword = "hansol2016";
        int port = 22;

        JSch jsch = new JSch();

        // 1차 서버 연결
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        localUserInfo lui = new localUserInfo();
        session.setUserInfo(lui);
        session.setConfig("StrictHostKeyChecking", "no");
        // create port from 2233 on local system to port 1521 on tunnelRemoteHost
        session.setPortForwardingL(4001, tunnelRemoteHost, 1521);
        session.connect();
        session.openChannel("direct-tcpip");

        // create a session connected to port 2233 on the local host.
        Session secondSession = jsch.getSession(user, "localhost", 4001);
        secondSession.setPassword(secondPassword);
        secondSession.setUserInfo(lui);
        secondSession.setConfig("StrictHostKeyChecking", "no");

        secondSession.connect(); // now we're connected to the secondary system
        Channel channel = secondSession.openChannel("exec");
        ((ChannelExec) channel).setCommand("hostname");

        channel.setInputStream(null);

        InputStream stdout = channel.getInputStream();

        channel.connect();

        while (true) {
            byte[] tmpArray = new byte[1024];
            while (stdout.available() > 0) {
                int i = stdout.read(tmpArray, 0, 1024);
                if (i < 0) break;
                outputBuffer.append(new String(tmpArray, 0, i));
            }
            if (channel.isClosed()) {
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
        }
        stdout.close();

        channel.disconnect();

        secondSession.disconnect();
        session.disconnect();

        System.out.print(outputBuffer.toString());
    }

    /**
     * com.jcraft.jsch.UserInfo 구현 클래스
     */
    class localUserInfo implements UserInfo {

        String passwd;
        public String getPassword() {
            return passwd;
        }
        public boolean promptYesNo(String str) {
            return true;
        }
        public String getPassphrase() {
            return null;
        }
        public boolean promptPassphrase(String message) {
            return true;
        }
        public boolean promptPassword(String message) {
            return true;
        }
        public void showMessage(String message) {
        }
    }

}