package org.egovframe.cmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.*;
import java.util.*;

/**
 *  Class Name : EgovProperties.java
 *  Description : properties값들을 파일로부터 읽어와   Globals클래스의 정적변수로 로드시켜주는 클래스로
 *   문자열 정보 기준으로 사용할 전역변수를 시스템 재시작으로 반영할 수 있도록 한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *	 2011.07.20    서준식 	      Globals파일의 상대경로를 읽은 메서드 추가
 *   2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class EgovProperties {
	private EgovProperties() {
		throw new IllegalStateException("Utility class");
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	//프로퍼티값 로드시 에러발생하면 반환되는 에러문자열
	public static final String ERR_CODE = " EXCEPTION OCCURRED";

	//파일구분자
	static final String FILE_SEPARATOR = System.getProperty("file.separator");

	static final String PROFILES = System.getProperty("spring.profiles.active");

	public static final String GLOBALS_PROPERTIES_FILE = "classpath:" + FILE_SEPARATOR + "application.properties";

	public static final String GLOBALS_PROPERTIES_FILE2 = "classpath:" + FILE_SEPARATOR + "application-" + PROFILES + ".properties";

	/**
	 * 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 값을 반환한다(Globals.java 전용)
	 * @param keyName String
	 * @return String
	*/
	public static String getProperty(String keyName) {
		String value = ERR_CODE;
		value = "";
		
        Resource resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
			    .getResource(GLOBALS_PROPERTIES_FILE);
		
        debug(GLOBALS_PROPERTIES_FILE + " : " + keyName);
		
		try (InputStream in = resources.getInputStream()) {
			Properties props = new Properties(); 
			props.load(new BufferedInputStream(in));
			value = props.getProperty(keyName).trim();
		} catch (NullPointerException ex) {
			debug(ex);
			value = getProperty(keyName, GLOBALS_PROPERTIES_FILE2);
		} catch (FileNotFoundException fne) {
			debug(fne);
		} catch (IOException ioe) {
			debug(ioe);
		}
		return value;
	}
	public static String getProperty(String keyName, String propertiesFile) {
		String value = ERR_CODE;
		value = "";

		Resource resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
				.getResource(propertiesFile);

		debug(propertiesFile + " : " + keyName);

		try (InputStream in = resources.getInputStream()) {
			Properties props = new Properties();
			props.load(new BufferedInputStream(in));
			value = props.getProperty(keyName).trim();
		} catch (FileNotFoundException fne) {
			debug(fne);
		} catch (IOException ioe) {
			debug(ioe);
		}
		return value;
	}
	/**
	 * 주어진 프로파일의 내용을 파싱하여 (key-value) 형태의 구조체 배열을 반환한다.
	 * @param property String
	 * @return ArrayList
	 */
	@SuppressWarnings("unused")
	public static List<Map<String, String>> loadPropertyFile(String property) {

		// key - value 형태로 된 배열 결과
		ArrayList<Map<String, String>> keyList = new ArrayList<>();

		String src = EgovWebUtil.filePathBlackList(property.replace("\\", FILE_SEPARATOR).replace("/", FILE_SEPARATOR));
		try (FileInputStream fis = new FileInputStream(src)) {

			File srcFile = new File(src);
			if (srcFile.exists()) {

				Properties props = new Properties();
				props.load(new BufferedInputStream(fis));

				int i = 0;
				Enumeration<?> plist = props.propertyNames();
				if (plist != null) {
					while (plist.hasMoreElements()) {
						Map<String, String> map = new HashMap<>();
						String key = (String)plist.nextElement();
						map.put(key, props.getProperty(key));
						keyList.add(map);
					}
				}
			}
		} catch (FileNotFoundException ex) {
			debug("FileNotFoundException:" + ex);
		} catch (IOException ex) {
			debug("IOException:" + ex);
		}
		return keyList;
	}

	/**
	 * 시스템 로그를 출력한다.
	 * @param obj Object
	 */
	private static void debug(Object obj) {
		if (obj instanceof Exception) {
			LOGGER.debug("IGNORED: {}", ((Exception)obj).getMessage());
		}
	}
}
