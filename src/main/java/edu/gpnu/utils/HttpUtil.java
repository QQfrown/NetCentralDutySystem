package edu.gpnu.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    public static String doGet(String url,String params){
        String requestUrl = url + "?"+params;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            URL url1 = new URL(requestUrl);
           httpURLConnection = (HttpURLConnection) url1.openConnection();

           httpURLConnection.setRequestMethod("GET");
           httpURLConnection.setConnectTimeout(15000);
           httpURLConnection.setReadTimeout(60000);
           httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

           httpURLConnection.connect();

           //请求响应之后
           if (httpURLConnection.getResponseCode() == 200){
               inputStream = httpURLConnection.getInputStream();
               bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                       "utf-8"));

               StringBuffer sb = new StringBuffer();
               String temp = null;
               while ((temp = bufferedReader.readLine()) != null){
                   sb.append(temp);
                   sb.append("\r\n");
               }
               result = sb.toString();
           }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpURLConnection.disconnect();
        }
        return result;
    }

    public static String doPost(String url,String params,String authorization){
        String result = null;
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader bufferedReader = null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            if (authorization != null){
                httpURLConnection.setRequestProperty("Authorization",authorization);
            }
            os = httpURLConnection.getOutputStream();
            os.write(params.getBytes());
            os.flush();

            if (httpURLConnection.getResponseCode() == 200){
                is = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String temp = null ;
                StringBuffer sb = new StringBuffer();
                while ((temp = bufferedReader.readLine()) != null){
                    sb.append(temp);
                    sb.append("/r/n");
                }
                result = sb.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpURLConnection.disconnect();
        }
        return result;
    }
}
