package com.hl.rsa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hl.rsa.config.LogisticsStatusCode;
import com.hl.rsa.config.ResponseCode;
import com.hl.rsa.config.WlException;
import com.hl.rsa.config.WlTyptCode;
import org.apache.commons.lang3.StringUtils;
import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>描述：物流轨迹信息传输 加密、解密、验签 工具类
 * <p>时间：2021/5/21
 * @author hh
 */
public final class RsaUtil {

    private static final String ALGO = "RSA";
    private static final String CHARSET = "UTF-8";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS  = "SHA256WithRSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 加密模长
     */
    private static final int KEY_LEN = 1024;

    /**
     * 生成随机密钥对，打印到控制台
     * @return Map<String,Object>
     * @throws NoSuchAlgorithmException
     */
    public static Map<String,Object> createKeyPair() throws NoSuchAlgorithmException {
            //
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGO);
            //初始化密钥对生成器，指定秘钥大小
            //keyPairGenerator.initialize(1024,new SecureRandom());
            keyPairGenerator.initialize(KEY_LEN);
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //得到私钥
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            //得到私钥字符
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            //得到公钥字符
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            HashMap<String, Object> map = new HashMap(2);
            map.put(PRIVATE_KEY, privateKeyString);
            map.put(PUBLIC_KEY, publicKeyString);
            System.out.println("输出私钥： = " +  privateKeyString);
            System.out.println("输出公钥： = " + publicKeyString);
            return map;
    }


    /**
     * 生成随机密钥对 ,指定存放位置
     * @param filePath 生成文件全路径名
     */
    public static void createKeyPairFile(String filePath) {

        FileWriter priWriter = null;
        FileWriter pubWriter = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGO);
            //初始化密钥对生成器，秘钥大小为96-1024 位
            //keyPairGenerator.initialize(1024,new SecureRandom());
            keyPairGenerator.initialize(KEY_LEN);
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //得到私钥
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            //得到私钥字符
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            //得到公钥字符
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("输出私钥： = " +  privateKeyString);
            System.out.println("输出公钥： = " + publicKeyString);
            priWriter = new FileWriter(filePath + "/private.key");
            pubWriter = new FileWriter(filePath + "/public.key");
            priWriter.write(privateKeyString);
            pubWriter.write(publicKeyString);
            priWriter.close();
            pubWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                priWriter.close();
                pubWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据指定的盐值，每次生成相同的密钥对
     * @param salt 盐值
     * @return Map<String,Object>
     * @throws Exception
     */
    public static Map<String,Object> createKeyPairBySalt(String salt) throws Exception {
            //
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGO);
            //初始化密钥对生成器，秘钥大小为96-1024 位
            keyPairGenerator.initialize(KEY_LEN,new SecureRandom(salt.getBytes(CHARSET)));
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //得到私钥
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            //得到私钥字符
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            //得到公钥字符
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            HashMap<String, Object> map = new HashMap(2);
            map.put(PRIVATE_KEY, privateKeyString);
            map.put(PUBLIC_KEY, publicKeyString);
            System.out.println("输出私钥： = " +  privateKeyString);
            System.out.println("输出公钥： = " + publicKeyString);
            return map;
    }


    /**
     * 从map对象中获取【私钥】
     * @param keyMap 含有公私钥的map对象
     * @return String 私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        try {
            return (String)keyMap.get(PRIVATE_KEY);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    /**
     * 从map对象中获取【公钥】
     * @param keyMap 含有公私钥的map对象
     * @return String 公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        try {
            return (String)keyMap.get(PUBLIC_KEY);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }


    /**
     * <p>从文件中获取公钥/私钥<br>
     * <p>根据文件后缀名区分公钥或者私钥<p>
     * @param filePath
     * @return String 公钥/私钥
     */
    public static String getKeyByFile(String filePath){
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isBlank(filePath)){
            throw new WlException("文件路径不能为空！");
        }
        if (filePath.endsWith("/public.key") || filePath.endsWith("/private.key")){
            try {
                bufferedReader = new BufferedReader(new FileReader(filePath));
                String readLine;
                while ((readLine = bufferedReader.readLine()) != null){
                    builder.append(readLine);
                }
                bufferedReader.close();
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            throw new WlException("文件后缀不符合要求！");
        }
        return builder.toString();
    }

    /**
     * 从字符串中获取【私钥】<br>
     * 获取私钥使用 PKCS8EncodedKeySpec
     * @param privateKeyString Base64编码后的秘钥字符串
     * @return PrivateKey 私钥实体类
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyByStr(String privateKeyString) throws Exception {
        PrivateKey rsaprivateKey = null;
        if (StringUtils.isBlank(privateKeyString)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        byte[] decode = Base64.getDecoder().decode(privateKeyString.getBytes(StandardCharsets.UTF_8));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        //使用 PKCS8EncodedKeySpec
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
        rsaprivateKey = keyFactory.generatePrivate(keySpec);
        return rsaprivateKey;
    }

    /**
     * 从字符串中获取【公钥】<br>
     * 获取私钥使用 X509EncodedKeySpec
     * @param publicKeyString Base64编码后的公钥字符串
     * @return PublicKey 公钥实体类
     * @throws Exception
     */
    public static PublicKey getPublicKeyByStr(String publicKeyString) throws Exception {
        PublicKey rsaprivateKey = null;
        if (StringUtils.isBlank(publicKeyString)) {
            throw new WlException(ResponseCode.ERROR08.toString());
        }
        byte[] decode = Base64.getDecoder().decode(publicKeyString.getBytes(StandardCharsets.UTF_8));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
        rsaprivateKey = keyFactory.generatePublic(keySpec);
        return rsaprivateKey;
    }


    /**
     * 公钥加密过程
     * @param publicKey 公钥
     * @param textData 明文数据（需要加密数据）
     * @return String 加密后的数据
     * @throws Exception
     */
    public static String encryptPublic(String publicKey,String textData) throws Exception {
        String encryptPublic = null;
        if (StringUtils.isBlank(publicKey)) {
            throw new WlException(ResponseCode.ERROR08.toString());
        }
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR10.toString());
        }
        //字符串转公钥
        PublicKey publicKeyByStr = RsaUtil.getPublicKeyByStr(publicKey);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyByStr);
        byte[] bytes = cipher.doFinal(textData.getBytes(StandardCharsets.UTF_8));
        encryptPublic = Base64.getEncoder().encodeToString(bytes);
        return encryptPublic;
    }

    /**
     * 公钥分段加密（大数据加密——长度超过117）
     * @param publicKey 公钥
     * @param textData 明文数据（需要加密数据）
     * @return String 加密后的数据
     * @throws Exception
     */
    public static String encryptPublicChunk(String publicKey,String textData) throws Exception{
        if (StringUtils.isBlank(publicKey)) {
            throw new WlException(ResponseCode.ERROR08.toString());
        }
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR10.toString());
        }
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE,RsaUtil.getPublicKeyByStr(publicKey));
        byte[] bytes = textData.getBytes(StandardCharsets.UTF_8);
        int inputLen = bytes.length;
        int offSet = 0;
        byte[] cache;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        //对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedDate = out.toByteArray();
        out.close();
        return Base64.getEncoder().encodeToString(encryptedDate);
    }


    /**
     *公钥解密
     * @param publicKey  Base64编码后的公钥字符串
     * @param encryptData Base64编码后的加密数据
     * @throws Exception
     */
    public static String decryptPublic(String publicKey,String encryptData) throws Exception {
        String encryptPublic = null;
        if (StringUtils.isBlank(publicKey)) {
            throw new WlException(ResponseCode.ERROR08.toString());
        }
        if (StringUtils.isBlank(encryptData)) {
            throw new WlException(ResponseCode.ERROR11.toString());
        }
        //字符串转公钥
        PublicKey publicKeyByStr = RsaUtil.getPublicKeyByStr(publicKey);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, publicKeyByStr);
        byte[] decode = Base64.getDecoder().decode(encryptData);
        byte[] bytes = cipher.doFinal(decode);
        encryptPublic = new String(bytes, StandardCharsets.UTF_8);
        return encryptPublic;
    }


    /**
     *公钥分段解密（大数据加密——长度超过128）
     * @param publicKey  Base64编码后的公钥字符串
     * @param encryptData Base64编码后的加密数据
     * @throws Exception
     */
    public static String decryptPublicChunk(String publicKey,String encryptData) throws Exception{
        if (StringUtils.isBlank(publicKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (StringUtils.isBlank(encryptData)) {
            throw new WlException(ResponseCode.ERROR11.toString());
        }
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE,RsaUtil.getPublicKeyByStr(publicKey));
        byte[] bytes = Base64.getDecoder().decode(encryptData);
        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        //对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedDate = out.toByteArray();
        out.close();
        return new String(decryptedDate,StandardCharsets.UTF_8);
    }

    /**
     * 私钥加密过程
     * @param privateKey Base64编码后的私钥字符串
     * @param textData Base64编码后的私钥数据
     * @throws Exception
     */
    public static String encryptPrivate(String privateKey,String textData) throws Exception {
        if (StringUtils.isBlank(privateKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR10.toString());
        }
        String encryptPrivate = null;
        //字符串转私钥
        PrivateKey privateKeyByStr = RsaUtil.getPrivateKeyByStr(privateKey);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, privateKeyByStr);
        byte[] bytes = cipher.doFinal(textData.getBytes(StandardCharsets.UTF_8));
        encryptPrivate = Base64.getEncoder().encodeToString(bytes);

        return encryptPrivate;
    }

    /**
     * 私钥分段加密（大数据加密——长度超过117）
     * @param privateKey Base64编码后的私钥字符串
     * @param textData Base64编码后的私钥数据
     * @throws Exception
     */
    public static String encryptPrivateChunk(String privateKey,String textData) throws Exception{
        if (StringUtils.isBlank(privateKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR10.toString());
        }
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE,RsaUtil.getPrivateKeyByStr(privateKey));
        byte[] bytes = textData.getBytes(StandardCharsets.UTF_8);

        int inputLen = bytes.length;
        int offSet = 0;
        byte[] cache;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        //对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedDate = out.toByteArray();
        out.close();
        return Base64.getEncoder().encodeToString(encryptedDate);
    }

    /**
     * 私钥解密过程
     * @param privateKey Base64编码后的私钥字符串
     * @throws Exception
     */
    public static String decryptPrivate(String privateKey,String encryptData) throws Exception {
        String encryptPrivate = null;
        if (StringUtils.isBlank(privateKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (StringUtils.isBlank(encryptData)) {
            throw new WlException("要解密值为空，请设置");
        }
        //字符串转私钥
        PrivateKey privateKeyByStr = RsaUtil.getPrivateKeyByStr(privateKey);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, privateKeyByStr);
        byte[] decode = Base64.getDecoder().decode(encryptData);
        byte[] bytes = cipher.doFinal(decode);
        encryptPrivate = new String(bytes, StandardCharsets.UTF_8);
        return encryptPrivate;
    }


    /**
     * 私钥分段解密（大数据加密——长度超过128）
     * @param privateKey Base64编码后的私钥字符串
     * @param encryptData Base64编码后的私钥数据
     * @throws Exception
     */
    public static String decryptPrivateChunk(String privateKey,String encryptData) throws Exception{
        if (StringUtils.isBlank(privateKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (StringUtils.isBlank(encryptData)) {
            throw new WlException(ResponseCode.ERROR11.toString());
        }
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE,RsaUtil.getPrivateKeyByStr(privateKey));
        byte[] bytes = Base64.getDecoder().decode(encryptData);
        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        //对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedDate = out.toByteArray();
        out.close();
        return new String(decryptedDate,StandardCharsets.UTF_8);
    }

    /**
     * <p>私钥加签（指定编码类型）<p>
     * @param textData 明文数据（也可以是解密后的数据当做明文数据）
     * @param privateKey Base64编码后的私钥字符串
     * @param encode 编码格式
     * @throws Exception
     */
    public static String sign(String textData,String privateKey,String encode) throws Exception {
        if (StringUtils.isBlank(privateKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR12.toString());
        }
        String signPri = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        PrivateKey aPrivate = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(aPrivate);
        signature.update(textData.getBytes(encode));
        byte[] sign = signature.sign();
        signPri = Base64.getEncoder().encodeToString(sign);
        return signPri;
    }

    /**
     * <p>私钥加签私钥加签（不指定编码类型，默认utf-8）<p>
     * @param textData 明文数据（也可以是解密后的数据当做明文数据）
     * @param privateKey Base64编码后的私钥字符串
     * @throws Exception
     */
    public static String sign(String textData,String privateKey) throws Exception {
        if (StringUtils.isBlank(privateKey)) {
            throw new WlException(ResponseCode.ERROR09.toString());
        }
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR12.toString());
        }
        String signPri = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        PrivateKey aPrivate = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(aPrivate);
        signature.update(textData.getBytes(StandardCharsets.UTF_8));
        byte[] sign = signature.sign();
        signPri = Base64.getEncoder().encodeToString(sign);
        return signPri;
    }

    /**
     * <p>公钥验签（指定编码类型）<p>
     * @param textData 明文数据（也可以是加密后的数据当做明文数据）
     * @param sign 数字签名
     * @param publicKey Base64编码后的公钥字符串
     * @param encode 编码
     * @throws Exception
     */
    public static boolean doCheck(String textData,String sign,String publicKey,String encode) throws Exception {
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR13.toString());
        }
        if (StringUtils.isBlank(sign)) {
            throw new WlException(ResponseCode.ERROR14.toString());
        }
        if (StringUtils.isBlank(publicKey)) {
            throw new WlException(ResponseCode.ERROR08.toString());
        }
        boolean verify = false;
        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        byte[] decode = Base64.getDecoder().decode(publicKey);
        PublicKey aPublic = keyFactory.generatePublic(new X509EncodedKeySpec(decode));
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(aPublic);
        signature.update(textData.getBytes(encode));
        verify = signature.verify(Base64.getDecoder().decode(sign));
        return verify;
    }

    /**
     * <p>公钥验签（不指定编码类型，默认utf-8）<p>
     * @param textData 明文数据（也可以是加密后的数据当做明文数据）
     * @param sign 数字签名
     * @param publicKey Base64编码后的公钥字符串
     * @throws Exception
     */
    public static boolean doCheck(String textData,String sign,String publicKey) throws Exception {
        if (textData == null) {
            throw new WlException(ResponseCode.ERROR13.toString());
        }
        if (StringUtils.isBlank(sign)) {
            throw new WlException(ResponseCode.ERROR14.toString());
        }
        if (StringUtils.isBlank(publicKey)) {
            throw new WlException(ResponseCode.ERROR08.toString());
        }
        boolean verify = false;
        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        byte[] decode = Base64.getDecoder().decode(publicKey);
        PublicKey aPublic = keyFactory.generatePublic(new X509EncodedKeySpec(decode));
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(aPublic);
        signature.update(textData.getBytes(StandardCharsets.UTF_8));
        verify = signature.verify(Base64.getDecoder().decode(sign));
        return verify;
    }

    /**
     * 创建返回实体
     * @param code 状态码值
     * @param message 状态信息
     * @param data 数据
     * @return ResultMode 返回实体类
     */
    public static ResultMode create(String code, String message, Object data){
        long time = System.currentTimeMillis();
        return new ResultMode(code,message,time,data);
    }

    /**
     * <p>便捷创建一个请求实体 SendMode<p>
     * @param vendorId 商户ID
     * @param logisticsTrack 物流信息列表（list）
     * @param privateKey Base64编码后的私钥字符串
     * @throws Exception
     */
    public static SendMode creatResultMode(String vendorId, List<LogisticsTrack> logisticsTrack, String privateKey) throws Exception{
        if (StringUtils.isBlank(vendorId)){
            throw new WlException("商户ID不能为空");
        }
        if (logisticsTrack == null){
            throw new WlException("物流信息不能为null");
        }
        if (StringUtils.isBlank(privateKey)){
            throw new WlException("私钥不能为空");
        }
        //列表参数校验
        beforeProtertiesCheckList(logisticsTrack);
        Map<String,String> map = new HashMap<>();
        //将物流数据转成Json字符串
        String listJsonString = JSON.toJSONString(logisticsTrack);
        //时间用来校验是否请求失效
        map.put("time", String.valueOf(System.currentTimeMillis()));
        map.put("list",listJsonString);
        String mapString = JSON.toJSONString(map);
        //私钥加密（加密后的数据当做原明文数据使用来进行加签）
        String encryptPrivate = RsaUtil.encryptPrivateChunk(privateKey, mapString);
        //数字签名
        String sign = RsaUtil.sign(encryptPrivate, privateKey);
        //商户id，签名，明文数据(数据密文)
        SendMode sendMode = new SendMode(vendorId, sign, System.currentTimeMillis(), encryptPrivate);
        return sendMode;
    }


    /**
     * <p>便捷 解析数据、获取详细物流信息<p>
     * @param resultMode 接收到的请求实体
     * @param publicKey Base64编码后的公钥字符串
     * @param timeout 自定义物流数据超时时间
     * @return List<LogisticsTrack> 物流日志列表
     * @throws Exception
     */
    public static List<LogisticsTrack> getLogisticsTracks(SendMode resultMode, String publicKey, Long timeout) throws Exception{
        if (resultMode == null){
            throw new WlException(ResponseCode.ERROR15.getMessage());
        }
        if (StringUtils.isBlank(publicKey)){
            throw new WlException(ResponseCode.ERROR08.getMessage());
        }
        //获取数据
        String modeSign = resultMode.getSign();
        String modeMapData = resultMode.getData();
        //签名校验
        boolean check = RsaUtil.doCheck(modeMapData, modeSign, publicKey);
        if (!check){
            throw new WlException(ResponseCode.ERROR01.getMessage());
        }
        //时间校验
        //解密获取时间戳
        String decryptPublic = RsaUtil.decryptPublicChunk(publicKey, modeMapData);
        Map<String, String> map = JSON.parseObject(decryptPublic, new TypeReference<Map<String, String>>() {});
        Long date = Long.valueOf(map.get("time"));
        if ((date + timeout) < System.currentTimeMillis()){
            throw new WlException(ResponseCode.ERROR18.getMessage());
        }
        //获取物流信息
        String listString = map.get("list");
        List<LogisticsTrack> logisticsTrackList = JSON.parseObject(listString, new TypeReference<List<LogisticsTrack>>() {});
        return logisticsTrackList;
    }

    /**
     * <p>创建物流数据——参数校验<br>
     * (循环校验请求物流数据是否为空)<p>
     * @param logisticsTrack 详细物流信息列表（list）
     */
    public static void beforeProtertiesCheckList(List<LogisticsTrack> logisticsTrack){
        for (LogisticsTrack track : logisticsTrack) {
            if (track.getSortId() == null){
                throw new WlException("【sortId】 不能为空");
            }
            Long sortId = track.getSortId();
            if (StringUtils.isBlank(track.getOrdermainId())){
                throw new WlException("物流序号【"+sortId+"】：ordermainId 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsNo())){
                throw new WlException("物流序号【"+sortId+"】：logisticsNo 不能为空");
            }
            if (StringUtils.isBlank(track.getExpressName())){
                throw new WlException("物流序号"+sortId+"】：expressName 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsTypeCode()) || !track.getLogisticsTypeCode().matches("1|2|3")){
                throw new WlException("物流序号【"+sortId+"】：logisticsTypeCode 属性值应为：【1|2|3】");
            }
            if (StringUtils.isBlank(track.getLogisticsTypeName()) ||  !track.getLogisticsTypeName().matches("发货|退货|补发")){
                throw new WlException("物流序号【"+sortId+"】：logisticsTypeCode 属性值应为：【发货|退货|补发】");
            }
            if (StringUtils.isBlank(track.getOriginAddress())){
                throw new WlException("物流序号【"+sortId+"】：originAddress 不能为空");
            }
            if (StringUtils.isBlank(track.getDestinationsAddress())){
                throw new WlException("物流序号【"+sortId+"】：destinationsAddress 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsStatusCode()) || !track.getLogisticsStatusCode().matches("00|01|02|03")){
                throw new WlException("物流序号【"+sortId+"】：logisticsStatusCode 属性值应为：【00|01|02|03】");
            }
            if (StringUtils.isBlank(track.getLogisticsStatusName()) || !track.getLogisticsStatusName().matches("揽件|在途|签收|异常")){
                throw new WlException("物流序号【"+sortId+"】：logisticsStatusName 属性值应为：【揽件|在途|签收|异常】");
            }
            if (track.getLogisticsUpdateTime() == null){
                throw new WlException("物流序号【"+sortId+"】：logisticsUpdateTime 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsDetail())){
                throw new WlException("物流序号【"+sortId+"】：logisticsDetail 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsLocation())){
                throw new WlException("物流序号【"+sortId+"】：logisticsLocation 不能为空");
            }
        }
    }

    /**
     * <p>解析物流数据——参数校验<br>
     * (循环校验请求物流数据是否为空)<p>
     * @param logisticsTrack 详细物流信息列表（list）
     */
    public static void afterProtertiesCheckList(List<LogisticsTrack> logisticsTrack){
        for (LogisticsTrack track : logisticsTrack) {
            if (track.getSortId() == null){
                throw new WlException("【sortId】 不能为空");
            }
            Long sortId = track.getSortId();
            if (StringUtils.isBlank(track.getOrdermainId())){
                throw new WlException("物流序号【"+sortId+"】：ordermainId 不能为空");
            }
            if (StringUtils.isBlank(track.getOrderId())){
                throw new WlException("物流序号【"+sortId+"】：orderId 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsNo())){
                throw new WlException("物流序号【"+sortId+"】：logisticsNo 不能为空");
            }
            if (StringUtils.isBlank(track.getExpressName())){
                throw new WlException("物流序号"+sortId+"】：expressName 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsTypeCode()) || !track.getLogisticsTypeCode().matches("1|2|3")){
                throw new WlException("物流序号【"+sortId+"】：logisticsTypeCode 属性值应为：【1|2|3】");
            }
            if (StringUtils.isBlank(track.getLogisticsTypeName()) ||  !track.getLogisticsTypeName().matches("发货|退货|补发")){
                throw new WlException("物流序号【"+sortId+"】：logisticsTypeCode 属性值应为：【发货|退货|补发】");
            }
            if (StringUtils.isBlank(track.getOriginAddress())){
                throw new WlException("物流序号【"+sortId+"】：originAddress 不能为空");
            }
            if (StringUtils.isBlank(track.getDestinationsAddress())){
                throw new WlException("物流序号【"+sortId+"】：destinationsAddress 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsStatusCode()) || !track.getLogisticsStatusCode().matches("00|01|02|03")){
                throw new WlException("物流序号【"+sortId+"】：logisticsStatusCode 属性值应为：【00|01|02|03】");
            }
            if (StringUtils.isBlank(track.getLogisticsStatusName()) || !track.getLogisticsStatusName().matches("揽件|在途|签收|异常")){
                throw new WlException("物流序号【"+sortId+"】：logisticsStatusName 属性值应为：【揽件|在途|签收|异常】");
            }
            if (track.getLogisticsUpdateTime() == null){
                throw new WlException("物流序号【"+sortId+"】：logisticsUpdateTime 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsDetail())){
                throw new WlException("物流序号【"+sortId+"】：logisticsDetail 不能为空");
            }
            if (StringUtils.isBlank(track.getLogisticsLocation())){
                throw new WlException("物流序号【"+sortId+"】：logisticsLocation 不能为空");
            }
        }
    }
    /**
     * 测试方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8ADiSHcIdbDUFcrg7VYkLQvwod0CemYhIV2mkSOkaNRoT8LIEqx0fo+E+fJw4aGUXF1Zrsna6xazCNrLK8n8oN3xxNIQDfsilc3X+6TYeFiECyMGCeAghFNV1bRk2khq22xuryXNmNlnDjvQ3WjIdGk3roeBlf7Uv67Glnm1RNwIDAQAB";
        //发送端：
        //创建请求体
        SendMode creatResultMode = wltest();
        System.out.println("JSON.toJSONString(creatResultMode) = " + JSON.toJSONString(creatResultMode));

        //接收端：
        //接收请求体（设置自定义超时时间——60s）
        List<LogisticsTrack> logisticsTrack = RsaUtil.getLogisticsTracks(creatResultMode, pub, 60000L);
        for (LogisticsTrack track : logisticsTrack) {
            System.out.println("track.getLogisticsUpdateTime() = " + track.getLogisticsUpdateTime());
            System.out.println("logisticsTrack.getSignDate() = " + track.getSignDate());
            System.out.println("---------------------------------------");
            System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
        }
    }

    //测试方法
    private static SendMode wltest() throws Exception {
        String pri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALwAOJIdwh1sNQVyuDtViQtC/Ch3QJ6ZiEhXaaRI6Ro1GhPwsgSrHR+j4T58nDhoZRcXVmuydrrFrMI2ssryfyg3fHE0hAN+yKVzdf7pNh4WIQLIwYJ4CCEU1XVtGTaSGrbbG6vJc2Y2WcOO9DdaMh0aTeuh4GV/tS/rsaWebVE3AgMBAAECgYBiVhtBEp++lCMHvoHvR0ZvxZHv+LCsRNKsREzVye1j7/K8C2KSXo7VRftDnBp6xuBZu7NQkgDYoR1EWSgcT8uCcEGPWjlM12iXLBNQAygpCnMvtYRoRobdUr81M+C5a9hIlJvqOtaFF3hQFqMPDuF+izmibfo2g4cyPIV6eLLWQQJBANyPPfQWMpsFDtiDnShgZqLojsUoATuFUU58Bhi9o06EsS4FdPB1obo68nJFXodum23xz6HiXHJqJw5egX5rk+UCQQDaNav28JncYAG/+BYwfmZ6ekYvijoetJvYiYdSwpfFN1sSjpsL4xATOwTbig/zrZmYWC8PnEMFEM02hgzHbXbrAkEAvTsYiGbTHDWGuzSQjafH51VnyLe99vc2/wNhp7BsgF1QNo/v+K60Bzez816Y79FyAS5KWjX/4xo4UBe9Ol3KEQJBANSSnKe/0+Ofk5TTtaogOOzv2RfO7Lek8n2L3Mx+zSNOveXiDqER3kMdp6nHYardX1fN8Gqgq5lvuLp49FIDDWkCQQDTrTgBE1jTM7uVWSyWROJwZpzT4YXA5Pe+zg4weoth+k9YGqavMrZPObX5LTJghvK+dawYhBkJOj2i2b3KO58x";
        //构造数据
        LogisticsTrack track = new LogisticsTrack();
        track.setSortId(1L);
        track.setOrdermainId("220301AW106481");
        track.setLogisticsNo("999999999");
        track.setExpressName("顺丰");
        track.setLogisticsTypeCode(WlTyptCode.SEND.getCode());
        track.setLogisticsTypeName(WlTyptCode.SEND.getName());
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
