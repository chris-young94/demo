package com.first.test.demo.demo4.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.SecondaryTable;
import java.lang.reflect.Method;
import java.util.Set;

@Component
@Aspect
public class CacheRemoveAop {
    @Resource
    RedisTemplate<String, String> redis;

  //  private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 截获标有@CacheRemove的方法
     */
    @Pointcut(value = "(@annotation(com.first.test.demo.demo4.aop.CacheRemove   ))")
    private void pointcut() {
    }

    /**
     * 功能描述: 切面在截获方法返回值之后
     */
    @After(value = "pointcut()")
    private void process(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        //获取切入方法的数据
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入方法
        Method method = signature.getMethod();
        //获得注解
        CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);
        //注解解析
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        if (cacheRemove != null) {
            StringBuilder sb = new StringBuilder();
            String value = cacheRemove.value();
            if (!value.equals("")) {
                sb.append(value);
            }

            //需要移除的正则key
            String[] keys = cacheRemove.key();
            System.out.println(keys+"123456                 "+keys.length);
            if (1 >= keys.length){
                Set<String> allKeys = redis.keys("*");
                Long a = redis.delete(allKeys);
                System.out.println("a = " +a);
                if (a == 1){
                    System.out.println("del success");
                }
            } else {
                sb.append("::");
                System.out.println(sb);
                for (String key : keys) {
//                    Expression expression = parser.parseExpression(key);
//                    String value1 = expression.getValue(context, String.class);
//                    //指定清除的key的缓存
                    cleanRedisCache(sb.toString() + key);
                }
            }
        }
    }

    private void cleanRedisCache(String key) {
        if (key != null) {
            //删除缓存
            redis.delete(key);
        }
    }

}
