/*
 * Copyright (C) 2016. Miroslav Kopecky
 * This HttpUtils.java is part of robo4j.
 *
 *     robo4j is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     robo4j is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with robo4j .  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.robo4j.brick.client.util;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

/**
 *
 * Basic Http constants and utils methods
 *
 * Created by miroslavkopecky on 23/05/16.
 */
public final class HttpUtils {

    public static final String HTTP_HEADER_OK = "HTTP/1.0 200 OK";
    public static final String HTTP_HEADER_NOT =  "HTTP/1.0 501 Not Implemented";
    public static final String HTTP_HEADER_NOT_ALLOWED =  "HTTP/1.0 405 Method Not Allowed";
    public static final String PAGE_WELCOME = "welcome.html";
    public static final String PAGE_ERROR = "error.html";
    public static final String PAGE_SUCCESS = "success.html";
    public static final String PAGE_STATUS = "status.html";
    public static final String PAGE_EXIT = "exit.html";
    public static final String STRING_EMPTY = "";
    public static final String HTTP_COMMAND = "command";
    public static final String HTTP_AGENT_CACHE = "cache";
    private static final String NEXT_LINE = "\r\n";

    public static String setHeader(String responseCode, int length) throws IOException {
        return new StringBuilder(STRING_EMPTY)
                .append(responseCode).append(NEXT_LINE)
                .append("Date: ")
                .append(LocalDateTime.now())
                .append(NEXT_LINE)
                .append("Server: robo4j-client").append(NEXT_LINE)
                .append("Content-length: ").append(length).append(NEXT_LINE)
                .append("Content-type: text/html; charset=utf-8").append(NEXT_LINE).append(NEXT_LINE)
                .toString();
    }

}
