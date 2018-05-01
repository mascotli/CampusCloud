package com.mascot.campuscloud.dao;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import com.mascot.campuscloud.manager.constant.HbaseConst;
import com.mascot.campuscloud.manager.hadoop.HbaseConn;


public class CreateTable extends HbaseDAO{
	/**
     * 创建表
     * @category create 'tableName','family1','family2','family3'
     * @param tableName
     * @param family
     * @throws Exception
     */
    public static void createTable(String tableName, String[] family) throws IOException {
        Admin admin = HbaseConn.getConn().getAdmin();

        TableName tn = TableName.valueOf(tableName);
        HTableDescriptor desc = new HTableDescriptor(tn);
        for (int i = 0; i < family.length; i++) {
            desc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tn)) {
            System.out.println("createTable => table Exists!");
            admin.disableTable(tn);
            admin.deleteTable(tn);
            admin.createTable(desc);
        } else {
            admin.createTable(desc);
            System.out.println("createTable => create Success! => " + tn);
        }
    }
    
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException {
		String[] gid = {"gid"};
		String[] user_id = {"id"};
		String[] id_user = {"user"};
		String[] email_user = {"user"};
		String[] file = {"file"};
		String[] user_file = {"file"};
		String[] follow = {"name"};
		String[] followed = {"userid"};
		String[] share = {"content"};
		String[] shareed = {"shareid"};
		
		/*createTable(HbaseConst.TABLE_GID, gid);
		createTable(HbaseConst.TABLE_USERId, user_id);
		createTable(HbaseConst.TABLE_IDUSER, id_user);
		createTable(HbaseConst.TABLE_EMAILUSER, email_user);*/
		createTable(HbaseConst.TABLE_FILE, file);
		createTable(HbaseConst.TABLE_USERFILE, user_file);
		createTable(HbaseConst.TABLE_FOLLOW, follow);
		createTable(HbaseConst.TABLE_FOLLOWED, followed);
		createTable(HbaseConst.TABLE_SHARE, share);
		createTable(HbaseConst.TABLE_SHAREED, shareed);
	}
}
