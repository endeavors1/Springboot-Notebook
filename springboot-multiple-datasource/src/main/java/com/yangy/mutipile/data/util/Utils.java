/*
 * Copyright (c)  2023 TechSure Co.,Ltd.  All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package com.yangy.mutipile.data.util;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * @Title: Utils
 * @Package com.techsure.module.balantecmdb.util
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/2/20 10:27
 **/

public class Utils {

    private static Connection conn;

    /**
     * 数据库连接
     *
     * @return
     */
    public static Connection getSourceData() {

        String dburl = "jdbc:mysql://192.168.0.21:3306/bsm_djbx?characterEncoding=UTF-8";
        String username = "root";
        String password = "zanyue$2012";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dburl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 查询数据库获取结果
     *
     * @param sql "SQL语句"
     * @return 查询结果
     */
    public static Map<String, List<List<String>>> searchSql(String sql, Connection con) {

        //返回hashmap
        Map<String, List<List<String>>> resultMap = new HashMap<String, List<List<String>>>();
        //SQL查询结果列表，用于easyExcel写入数据
        List<List<String>> listMaps = new ArrayList<List<String>>();

        PreparedStatement pstmt = null;

        System.out.println("searchSql_<sql>: " + sql);

        //获取列名列表，用于easyExcel写入header
        List<List<String>> listField = new ArrayList<List<String>>();
        //列名key，用于resultset获取字段值
        List<String> fieldList = new ArrayList<String>();

        try {
            pstmt = con.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            //获取结果集resultSet的结果集元数据metaData
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            //列下标用1开始
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                //获取用于打印输出和显示的指定列的建议标题，包括别名
                String columnLabel = resultSetMetaData.getColumnLabel(i);
                listField.add(createHeader(columnLabel));
                fieldList.add(columnLabel);
            }
            while (result.next()) {
                //单行数据
                ArrayList<String> list = new ArrayList<String>();
                for (String string : fieldList) {
                    list.add(result.getString(string));
                }
                listMaps.add(list);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        resultMap.put("result", listMaps);
        resultMap.put("headers", listField);

        return resultMap;
    }

    /**
     * 生成单个列名列表
     *
     * @param headName 列名
     * @return
     */
    public static List<String> createHeader(String headName) {
        return new ArrayList<>(Arrays.asList(headName));
    }

    public static void main(String[] args) {
        File file = new File("D:\\app\\1.txt");
        String excelFilePathString = file.getAbsolutePath().replace("1.txt", "");
        String EXCEL_FILE_NAME = "text.xlsx";
        String fileName = excelFilePathString + "\\" + EXCEL_FILE_NAME;

        Connection conn = Utils.getSourceData();
        Map<String, List<List<String>>> listMaps = new HashMap<String, List<List<String>>>();
        listMaps = Utils.searchSql("select id,action,operator,operate_date,`table` from flow_audit limit 1000000", conn);//excel有限制100多万条数据 这里只取100w
        System.out.println("数据总行数：" + listMaps.get("result").size());
        System.out.println("写入Excel文件开始----------");
        Long t1 = System.currentTimeMillis();
        EasyExcel.write(fileName).autoCloseStream(Boolean.FALSE).head(listMaps.get("headers"))
                .sheet("easyExcel")
                .doWrite(listMaps.get("result"));

        Long t2 = System.currentTimeMillis();

        System.out.println("写入Excel文件结束，总用时：" + ((t2 - t1) / 1000.00) + "s");
    }

}

