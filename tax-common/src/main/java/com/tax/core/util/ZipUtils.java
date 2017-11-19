package com.tax.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtils
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月19日 下午7:16:08
 * @version v1.0
 */
public class ZipUtils {
	
	/**
	 * 压缩成zip
	 * @param srcDir 压缩文件夹路径 
	 * @param out    压缩文件输出流
	 * @throws RuntimeException  压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out)throws RuntimeException{
		System.out.println("压缩中....");
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile,zos,sourceFile.getName());
			zos.close();
			System.out.println("压缩..完成");
			long end = System.currentTimeMillis();
			System.out.println("压缩耗时：" + (end - start) +" ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 递归压缩方法
	 * @param sourceFile 源文件
	 * @param zos		 zip输出流
	 * @param name		 压缩后的名称
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name) throws Exception{
		byte[] buf = new byte[1024];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) > 0){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 空文件夹的处理
				zos.putNextEntry(new ZipEntry(name+"/"));
				// 没有文件，不需要文件的copy
				zos.closeEntry();
			}else {
				for (File file : listFiles) {
					// 注意：name需要带上父文件夹的名字加一斜杠,
					// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
					compress(file, zos, name + "/" + file.getName());
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File("c:/mytest.zip"));
		ZipUtils.toZip("D:/log", fos);
	}
}
