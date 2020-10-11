package pl.coderslab.driver.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlCreator {

    public static String mediaFileDownloadUrl(HttpServletRequest request, Long fileId) {

        if (isIdValid(fileId)){
            return "file does not uploaded";
        }

        StringBuilder result = contentPathBuilder(request);
        result.append("/file/download?fileId=");
        result.append(fileId);

        return result.toString();
    }

    public static String testDetailsUrl(HttpServletRequest request, Long testId) {
        if (isIdValid(testId)){
            return "Test does not created";
        }

        StringBuilder result = contentPathBuilder(request);
        result.append("/test/get?testId=");
        result.append(testId);

        return result.toString();
    }

    private static boolean isIdValid (Long id){
        return (id == null || id == 0);
    }

    private static StringBuilder contentPathBuilder(HttpServletRequest request) {
        int port = request.getServerPort();
        StringBuilder result = new StringBuilder();
        result.append(request.getScheme())
                .append("://")
                .append(request.getServerName());

        if ((request.getScheme().equals("http") && port != 80) || (request.getScheme().equals("https") && port != 443)) {
            result.append(':')
                    .append(port);
        }

        result.append(request.getContextPath());

        return result;
    }
}
