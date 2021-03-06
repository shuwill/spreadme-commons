/*
 * Copyright [2/28/20 2:55 PM] [shuwei.wang (c) wswill@foxmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spreadme.commons.http.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Http Client Request
 * @author shuwei.wang
 */
public interface HttpClientRequest extends HttpMessage, Closeable{

	/**
	 * Execute this request, resulting in a {@link HttpClientResponse} that can be read.
	 * @return the response result of the execution
	 * @throws IOException in case of I/O errors
	 */
	HttpClientResponse execute() throws IOException;

	/**
	 * Return the body of the message as an output stream.
	 * @return the output stream body (never {@code null})
	 * @throws IOException in case of I/O errors
	 */
	OutputStream getBody() throws IOException;
}
