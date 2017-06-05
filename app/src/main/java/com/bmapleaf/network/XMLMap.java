package com.bmapleaf.network;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

/**
 * Created by admin on 2017/5/4.
 */

public class XMLMap {
    public static void getMaps(Resources resources, int id, Map<String, String> map) throws XmlPullParserException, IOException {
        XmlResourceParser parser = resources.getXml(id);
        int type = parser.getEventType();
        while (type != END_DOCUMENT) {
            if (type == START_TAG) {
                String k = parser.getAttributeValue(null, "key");
                String v = parser.getAttributeValue(null, "value");
                if (k != null && v != null) {
                    map.put(k, v);
                }
            }
            type = parser.next();
        }
        parser.close();
    }

    public static String getValueByKey(Resources resources, int id, String key) throws XmlPullParserException, IOException {
        XmlResourceParser parser = resources.getXml(id);
        int type = parser.getEventType();
        String value = null;
        while (type != END_DOCUMENT) {
            if (type == START_TAG) {
                String k = parser.getAttributeValue(null, "key");
                if (key.equals(k)) {
                    value = parser.getAttributeValue(null, "value");
                    break;
                }
            }
            type = parser.next();
        }
        parser.close();
        return value;
    }
}
