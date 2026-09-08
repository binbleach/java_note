package com.huangjiabin.a03_rsa;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class test {
    public static void main(String[] args) {
        try {
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOiyX21RaUs-hf5H2Rtv3fjjXt40NzGh4lvy3_n2FYNjCNEqeD3D0vSXjBY6jzsRTc8W58zBfUaQqfCsJ7d1cn0mN-PMZPsK49tb09V7GjyuNjDOT9-llxbB5Z1iTfoNF4i9n8_A8g31NUwHsG01VN9MlxgjJXFfyaHP4Vslg03wIDAQAB";
            String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM6LJfbVFpSz6F_kfZG2_d-ONe3jQ3MaHiW_Lf-fYVg2MI0Sp4PcPS9JeMFjqPOxFNzxbnzMF9RpCp8Kwnt3VyfSY348xk-wrj21vT1XsaPK42MM5P36WXFsHlnWJN-g0XiL2fz8DyDfU1TAewbTVU30yXGCMlcV_Joc_hWyWDTfAgMBAAECgYAooT7Km4hhoDJqCqyY6YJvY65AKaCvF8gubLlDu7qOe7p5Rd4mFkEC-FUE1fH6Xi9YdzXg3tUhN7ibKKaxWvyNwmAVE-yxlHQu5rLBx5MEeYjOtdvS9Fu0kHUI6Yi_7CvfsuDjkz4cXwYT32Uq0eyM0Q_jxCbH2HQTjZmGlzfDIQJBAOhsFQnCfgzL2A7ydsIE19W9fJFMymtBozRuRfeCc2U2HgjcRn7p81OHF1NCFMN2K7uItP8I_BclSM6S6QO4a-0CQQDjfwHco-sI4heYTZYB13L8bhi1Xlxhw_1Q1cd8vqutq8xHaFZghGvLxN_9IJikk03MV2_Ic9wpk1N_PwIX5oJ7AkEA0VMGX5PbEotEO7IDYxoZiVbvKa2PYKns4vut6AyFAVKMGk7I7uSlUUUJrOKG8MMs_-lPmaHt6Kqbt_B4C_G8wQJBAJidMmWO_XR3IDcHDXPs0fGv9hlt1PGtJjndQDdaPheC4cahoxianSoUx-KqxFvbidxkZ9QEjZTChcgeP8xC0hMCQQCNbVnFm7vCSqvyKul5WwpwkRxYuIT0wcrplGQaGk_RqOHrEnzL38YbVyskafoE2nXqEraM3drHe-uqRJkUJ2yx";
            String str = "你好";
            String base64Str  = Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
            String decode = RSAUtils.encrypt(str, publicKey);
            System.out.println("加密后值为："+decode);
            String encrypt = RSAUtils.decrypt(decode, privateKey);
            System.out.println("解密后值为："+encrypt);
//            RSAUtils.getKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
