package com.gbsoft.rainfallcollector.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {

	public RandomUtil() {
	}
	
	/**
     * 자릿수(length) 만큼 랜덤한 숫자 + 문자 조합을 대문자/소문자에 따라 반환 받습니다.
     *
     * @param length      자릿수
     * @param isUpperCase 대문자 여부
     * @return 랜덤한 숫자 + 문자 조합의 문자열
     */
    public static String generateRandomMixStr(int length, boolean isUpperCase) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return isUpperCase ? sb.toString() : sb.toString().toLowerCase();
    }
    
    /**
     * 자릿수(length) 만큼 랜덤한 숫자를 반환 받습니다.
     *
     * @param length 자릿수
     * @return 랜덤한 숫자
     */
    public static int generateRandomNum(int length) {
        SecureRandom secureRandom = new SecureRandom();
        int upperLimit = (int) Math.pow(10, length);
        return secureRandom.nextInt(upperLimit);
    }
    
    /**
     * 자릿수(length) 만큼 랜덤한 숫자를 반환 받습니다.
     *
     * @param length 자릿수
     * @return 랜덤한 숫자의 문자열
     */
    public static String genneraterNum(int length) {
    	
    	Random random = new Random();		// 랜덤 함수 선언
		int createNum = 0;  				// 1자리 난수
		String ranNum = ""; 				// 1자리 난수 형변환 변수
		String resultNum = "";  			// 결과 난수
		
		for (int i = 0; i < length; i++) { 
            		
			createNum = random.nextInt(9);			// 0부터 9까지 올 수 있는 1자리 난수 생성
			ranNum =  Integer.toString(createNum);  // 1자리 난수를 String으로 형변환
			resultNum += ranNum;					// 생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
		}

		return resultNum;
    }

}
