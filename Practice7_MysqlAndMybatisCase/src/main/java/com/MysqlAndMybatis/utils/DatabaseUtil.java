package com.MysqlAndMybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {
    /**
     * 封装sqlsession对象，用来执行sql语句的
     * @return 返回sqlsession对象,用来执行配置文件中的sql语句
     * @throws IOException
     */
    public static SqlSession getSqlsession() throws IOException {
        //读取数据库配置文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");

        //加载配置文件
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //此sqlSession对象就是用来执行sql语句的
        SqlSession sqlSession = factory.openSession();
        return sqlSession;
    }
}
