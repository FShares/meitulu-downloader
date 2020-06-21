package club.geek66.downloader.meitulu;

import java.text.MessageFormat;
import java.util.*;

public class othertest {

    // register
//
//            2，
//            3，
//            4，

    // in java 11
    String[] registerArr = {
            "您手机注册的验证码为：【{0}】，如有问题请拨打客服电话：【{1}】",
            "您的注册验证码是【{0}】，请不要把验证码泄漏给其他人，如非本人请勿操作。【{1}】",
            ""
    };
    String[] LoginArr = {
            "您的登录密码是：【变量】。打死不要告诉别人！【变量2】",
            "您的本次登录校验码为：【变量】,15分钟内输入有效！【变量2】",
            "您的登录验证码是【变量】（5分钟内有效），请勿泄漏给他人。如非本人操作，请忽略本条消息。【变量2】"
    };

    List<String> register = Arrays.asList(registerArr);
    List<String> login = Arrays.asList(LoginArr);

    // template
    Map<String, List<String>> mapssss  = new HashMap<>() {{
        put("register", register);
        put("login", login);
    }};


    // 短信模板
    String message = "{0}，您好，仔细看过您的简历，觉得您比较适合我们公司{1}职位，请您{2}来我公司参加面试。我司地址：{3} ，电话{4}【{5}】";
    String message2 = "亲，感谢您选择【{0}】平台。您购买的{1}已经发货啦。【{0}】平台为你提供快递单号{2}，祝愿亲的生活充满幸福与欢乐。";
    MessageFormat messageFormat = new MessageFormat(message);
    Object[] array = new Object[]{"ZhangSan","Java Programmer", "明天上午九点", "Address", "12345678", "XXXX公司"};
    String value = messageFormat.format(array);
//        System.out.println(value);

//    messageFormat = new MessageFormat(message2);
//    array = new Object[]{"优购","Deno入门","5802xx0326553"};
//    value = messageFormat.format(array);
//
//        System.out.println(value);
}
