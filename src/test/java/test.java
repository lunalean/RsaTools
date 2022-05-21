import com.alibaba.fastjson.JSON;
import com.hl.rsa.LogisticsTrack;
import com.hl.rsa.ResultMode;
import com.hl.rsa.RsaUtil;
import com.hl.rsa.SendMode;
import com.hl.rsa.config.LogisticsStatusCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Time： 2021/5/21
 * @Author： hh
 * @Description：
 */

public class test {

    private static String pri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALwAOJIdwh1sNQVyuDtViQtC/Ch3QJ6ZiEhXaaRI6Ro1GhPwsgSrHR+j4T58nDhoZRcXVmuydrrFrMI2ssryfyg3fHE0hAN+yKVzdf7pNh4WIQLIwYJ4CCEU1XVtGTaSGrbbG6vJc2Y2WcOO9DdaMh0aTeuh4GV/tS/rsaWebVE3AgMBAAECgYBiVhtBEp++lCMHvoHvR0ZvxZHv+LCsRNKsREzVye1j7/K8C2KSXo7VRftDnBp6xuBZu7NQkgDYoR1EWSgcT8uCcEGPWjlM12iXLBNQAygpCnMvtYRoRobdUr81M+C5a9hIlJvqOtaFF3hQFqMPDuF+izmibfo2g4cyPIV6eLLWQQJBANyPPfQWMpsFDtiDnShgZqLojsUoATuFUU58Bhi9o06EsS4FdPB1obo68nJFXodum23xz6HiXHJqJw5egX5rk+UCQQDaNav28JncYAG/+BYwfmZ6ekYvijoetJvYiYdSwpfFN1sSjpsL4xATOwTbig/zrZmYWC8PnEMFEM02hgzHbXbrAkEAvTsYiGbTHDWGuzSQjafH51VnyLe99vc2/wNhp7BsgF1QNo/v+K60Bzez816Y79FyAS5KWjX/4xo4UBe9Ol3KEQJBANSSnKe/0+Ofk5TTtaogOOzv2RfO7Lek8n2L3Mx+zSNOveXiDqER3kMdp6nHYardX1fN8Gqgq5lvuLp49FIDDWkCQQDTrTgBE1jTM7uVWSyWROJwZpzT4YXA5Pe+zg4weoth+k9YGqavMrZPObX5LTJghvK+dawYhBkJOj2i2b3KO58x";
    private static String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8ADiSHcIdbDUFcrg7VYkLQvwod0CemYhIV2mkSOkaNRoT8LIEqx0fo+E+fJw4aGUXF1Zrsna6xazCNrLK8n8oN3xxNIQDfsilc3X+6TYeFiECyMGCeAghFNV1bRk2khq22xuryXNmNlnDjvQ3WjIdGk3roeBlf7Uv67Glnm1RNwIDAQAB";

    public static void main(String[] args) throws Exception {
        //发送端：
        //创建请求体
        //SendMode creatResultMode = wltest();
        //System.out.println("JSON.toJSONString(creatResultMode) = " + JSON.toJSONString(creatResultMode));
        //
        ////接收端：
        ////接收请求体（设置自定义超时时间——60s）
        //List<LogisticsTrack> logisticsTrack = RsaUtil.getLogisticsTracks(creatResultMode, pub, 60000L);
        //for (LogisticsTrack track : logisticsTrack) {
        //    System.out.println("track.getVendorId() = " + track.getVendorId());
        //    System.out.println("track.getLogisticsUpdateTime() = " + track.getLogisticsUpdateTime());
        //    System.out.println("logisticsTrack.getSignDate() = " + track.getSignDate());
        //    System.out.println("---------------------------------------");
        //    System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
        //}


        ResultMode ok = ResultMode.OK();
        System.out.println("JSON.toJSONString(ok) = " + JSON.toJSONString(ok));

        ResultMode error = ResultMode.ERROR("失败测试");
        System.out.println("JSON.toJSONString(error) = " + JSON.toJSONString(error));
        System.out.println("error = " + error);

    }

    //测试方法
    public static SendMode wltest() throws Exception {
        String pri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALwAOJIdwh1sNQVyuDtViQtC/Ch3QJ6ZiEhXaaRI6Ro1GhPwsgSrHR+j4T58nDhoZRcXVmuydrrFrMI2ssryfyg3fHE0hAN+yKVzdf7pNh4WIQLIwYJ4CCEU1XVtGTaSGrbbG6vJc2Y2WcOO9DdaMh0aTeuh4GV/tS/rsaWebVE3AgMBAAECgYBiVhtBEp++lCMHvoHvR0ZvxZHv+LCsRNKsREzVye1j7/K8C2KSXo7VRftDnBp6xuBZu7NQkgDYoR1EWSgcT8uCcEGPWjlM12iXLBNQAygpCnMvtYRoRobdUr81M+C5a9hIlJvqOtaFF3hQFqMPDuF+izmibfo2g4cyPIV6eLLWQQJBANyPPfQWMpsFDtiDnShgZqLojsUoATuFUU58Bhi9o06EsS4FdPB1obo68nJFXodum23xz6HiXHJqJw5egX5rk+UCQQDaNav28JncYAG/+BYwfmZ6ekYvijoetJvYiYdSwpfFN1sSjpsL4xATOwTbig/zrZmYWC8PnEMFEM02hgzHbXbrAkEAvTsYiGbTHDWGuzSQjafH51VnyLe99vc2/wNhp7BsgF1QNo/v+K60Bzez816Y79FyAS5KWjX/4xo4UBe9Ol3KEQJBANSSnKe/0+Ofk5TTtaogOOzv2RfO7Lek8n2L3Mx+zSNOveXiDqER3kMdp6nHYardX1fN8Gqgq5lvuLp49FIDDWkCQQDTrTgBE1jTM7uVWSyWROJwZpzT4YXA5Pe+zg4weoth+k9YGqavMrZPObX5LTJghvK+dawYhBkJOj2i2b3KO58x";
        //构造数据
        LogisticsTrack track = new LogisticsTrack();
        track.setSortId(1L);
        track.setOrdermainId("220301AW106481");
        track.setLogisticsNo("999999999");
        track.setExpressName("顺丰");
        track.setLogisticsTypeCode("1");
        track.setLogisticsTypeName("消费");
        track.setOriginAddress("北京");
        track.setDestinationsAddress("上海");
        track.setLogisticsStatusCode(LogisticsStatusCode.COLLECT.getCode());
        //track.setLogisticsStatusCode("00");
        track.setLogisticsStatusName(LogisticsStatusCode.COLLECT.getName());
        //track.setLogisticsStatusName("揽件");
        track.setLogisticsUpdateTime(LocalDateTime.now());
        track.setLogisticsDetail("上一站：北京市————下一站：上海");
        track.setLogisticsLocation("天津");
        track.setLogisticsRemark("这是用来测试的物流信息");
        List<LogisticsTrack> list = new ArrayList<>();
        list.add(track);
        RsaUtil.beforeProtertiesCheckList(list);
        //获取
        SendMode sendMode = RsaUtil.creatResultMode("06W", list, pri);
        return sendMode;

    }

















}


class User1{
    String name;
    int age;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    public static void main(String[] args) {
        String a= "123";
        System.out.println(a);
        System.out.println((Object) a);


    }




}