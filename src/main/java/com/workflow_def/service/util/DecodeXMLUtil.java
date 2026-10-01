package com.workflow_def.service.util;

import java.util.Base64;

public interface DecodeXMLUtil {

    Base64.Decoder decoder = Base64.getDecoder();

    static byte[] decodeXML(String encodedXML) {
        return decoder.decode(encodedXML);
    }

}