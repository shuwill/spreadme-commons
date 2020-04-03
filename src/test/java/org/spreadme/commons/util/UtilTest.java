/*
 *    Copyright [2019] [shuwei.wang (c) wswill@foxmail.com]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.spreadme.commons.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.spreadme.commons.crypt.Algorithm;
import org.spreadme.commons.crypt.Digest;
import org.spreadme.commons.id.IdentifierGenerator;
import org.spreadme.commons.id.support.PrefixedLeftNumericGenerator;
import org.spreadme.commons.id.support.SnowflakeLongGenerator;
import org.spreadme.commons.id.support.TimeBasedIdentifierGenerator;
import org.spreadme.commons.lang.Console;
import org.spreadme.commons.lang.Dates;
import org.spreadme.commons.lang.ImageFormats;
import org.spreadme.commons.lang.Randoms;
import org.spreadme.commons.system.SystemInfo;
import org.spreadme.commons.system.SystemMonitor;

/**
 * @author shuwei.wang
 */
public class UtilTest {

	@Test
	public void testStringUtil() throws Exception {
		System.out.println(Arrays.toString(Randoms.nextBytes(3)));
		Concurrents.startAll(10, () -> {
			String result = StringUtil.randomString(8) + " :: " + StringUtil.randomString("-+*", 1);
			Console.info(result + " :: " + Digest.toHexString(new ByteArrayInputStream(result.getBytes()), Algorithm.MD5));
			return result;
		});
		Console.info(StringUtil.replace("wsweiwwww//\\w/", "w", "90"));
		String unicode = StringUtil.stringToUnicode("TEst^&测试");
		Console.info(unicode);
		Console.info(StringUtil.unicodeToString(unicode));
	}

	@Test
	public void testId() {
		IdentifierGenerator<String> timeBasedGenerator = new TimeBasedIdentifierGenerator();
		IdentifierGenerator<String> leftNumericGenerator = new PrefixedLeftNumericGenerator(StringUtil.randomString(1), true, 3);
		IdentifierGenerator<Long> longIdentifierGenerator = new SnowflakeLongGenerator(1, 1);
		for (int i = 0; i < 100; i++) {
			Console.info("timebase: %s, numeric: %s, longid: %s",
					timeBasedGenerator.nextIdentifier(),
					leftNumericGenerator.nextIdentifier(),
					longIdentifierGenerator.nextIdentifier());
		}
	}

	@Test
	public void testDates() throws Exception {
		final int poolSize = 8;
		final String formatter = "HH:mm:ss dd.MM.yyyy";
		final SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		ExecutorService executor = Executors.newFixedThreadPool(poolSize);

		Concurrents.startAll(poolSize, () -> {
			String text = "19:30:55 03.05.2015";
			//Date date = sdf.parse(dateCreate);
			Date date = Dates.parse(text, formatter);
			Console.info("%s parse %s to %s", Thread.currentThread().getName(), text, date);
		}, executor);

		Console.info("今天开始时间: %s", Dates.getStartOfDate(new Date()));
		Console.info("今天结束时间: %s", Dates.getEndOfDate(new Date()));
		Console.info("100天前的时间: %s", Dates.getDate(new Date(), ChronoUnit.DAYS, -100));

		Console.info("时间戳: %s", Dates.getTimestamp());

		Date oldDate = Dates.parse("19:30:55 03.05.2015", formatter);
		Duration duration = Dates.getDuration(new Date(), oldDate);
		Console.info(duration.toDays());

		Console.info(Dates.format(new Date(), formatter));
	}

	@Test
	public void testSystemInfo() {
		SystemMonitor monitor = new SystemMonitor();
		Console.info(monitor.getSystemInfo());
	}

	@Test
	public void testDateParse() {

	}

	@Test
	public void testTextToImage() throws IOException {
		BufferedImage image = ImageUtil.toImage("Test测试", new Font(Font.SANS_SERIF, Font.PLAIN, (int) (50 * 0.75)), Color.BLACK);
		ImageIO.write(image, ImageFormats.PNG.getName(),
				new File(ClassUtil.getClassPath() + SystemInfo.FILE_SEPARATOR + "water.png"));
	}

	@Test
	public void testNetUtil() {
		final String host = NetUtil.getHostByUrl("https://ci.qiyuesuo.me");
		final String hostIp = NetUtil.getIpByDomain(host);
		Console.info(host);
		Console.info(hostIp);
		Console.info(NetUtil.isConnected("https://ci.qiyuesuo.me", 5000));
		Console.info(NetUtil.isReachable(hostIp, 5000));
	}

	@Test
	public void testStringifyException() {
		try {
			throw new IllegalStateException("StringifyException test");
		}
		catch (Exception ex) {
			Console.info(StringUtil.stringifyException(ex));
		}
	}

	@Test
	public void testStringLength() {
		String string = "Hello你好";
		Console.info(string.length());
		Console.info(string.codePointCount(0, string.length()));
	}
}
