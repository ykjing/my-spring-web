package com.ticketcenter.user.secure;

import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class CodeUtils {

	public Pair< String, String> generateHash(String pwd) {
		String randomStr = randomString();
		String hexpwd = hexWithSalt(randomStr, pwd);
		return new ImmutablePair<>( randomStr, hexpwd);
	}

	public String hexWithSalt(String salt, String value) {
		return DigestUtils.sha1Hex(value+salt);
	}
	
	public String randomString() {
		return RandomStringUtils.secure().nextPrint(10);
	}
	
}
