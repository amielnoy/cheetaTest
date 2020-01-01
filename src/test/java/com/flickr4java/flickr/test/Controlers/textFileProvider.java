/**
 * 
 */
package com.flickr4java.flickr.test.Controlers;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Gladson Antony
 * @Date 21-Feb-2017
 */
public class textFileProvider
{
	@DataProvider(name="textFileNameAsMethodName")
	public static Object[][] excelSheetNameAsMethodName(Method method) throws Exception
	{
		File file = new File("src/test/resources/TestDrrivenData/"+method.getName()+".txt");
		System.out.println("Opening Excel File:" +file.getAbsolutePath());
		Object testObjArray[][] = getTwoDimArrayFromTxtFile(file.getAbsolutePath());
		return testObjArray;
	}

	public static Object[][] getTwoDimArrayFromTxtFile(String fileFullPath) throws IOException {
		FileReader fileReader = new FileReader(new File(fileFullPath));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
        //TBD calculate row elements using split
		String [] strLineArr=lines.get(0).split(",");

		int lineSize=strLineArr.length;
		String[][] testObjArray =new String[lines.size()][lineSize];
		String [] strLineArrNoColons;
		for(int i=0;i<lines.size();i++) {
			strLineArrNoColons=lines.get(i).split(",");
			for (int j = 0; j < strLineArrNoColons.length; ++j)
				testObjArray[i][j] =strLineArrNoColons[j];
		}


		return testObjArray;
	}
}
