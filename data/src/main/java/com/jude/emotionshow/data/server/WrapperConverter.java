package com.jude.emotionshow.data.server;

import com.google.gson.Gson;
import com.jude.emotionshow.domain.api.Code;
import com.jude.emotionshow.domain.api.Wrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by Mr.Jude on 2015/8/24.
 * 剥离返回数据最外一层的数据解析器
 *
 */
public class WrapperConverter implements Converter {
    private static final String CHARSET_DEFAULT = "utf-8";
    private static final int BUFFER_SIZE = 0x400;
    Gson gson;

    public WrapperConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String result = getString(body);

        JSONObject jsonObject = null;
        int status = 0;

        try {
            jsonObject = new JSONObject(result);
            status = jsonObject.getInt(Wrapper.STATUS);
        } catch (JSONException e) {
            throw new ServiceException(Code.Analyze_Error,"数据解析错误");
        }


        if (status == Code.Success){
            if (jsonObject.has(Wrapper.DATA))
                try {
                    return gson.fromJson(jsonObject.getString(Wrapper.DATA), type);
                } catch (JSONException e) {
                    throw new ServiceException(Code.Analyze_Error,"数据解析错误");
                }
            else
                return null;

        }else{

            String info = "";
            if (jsonObject.has(Wrapper.INFO))
                try {
                    info = jsonObject.getString(Wrapper.INFO);
                } catch (JSONException e) {
                }
            throw new ServiceException(status,info);
        }
    }



    private String getString(TypedInput body) throws ConversionException {
        String charset = CHARSET_DEFAULT;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        String result;
        try {
            isr = new InputStreamReader(body.in(), charset);
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[BUFFER_SIZE];
            for (; ; ) {
                int res = isr.read(buf, 0, buf.length);
                if (res < 0) {
                    break;
                }
                sb.append(buf, 0, res);
            }
            result = sb.toString();
            return result;
        } catch (IOException e) {
            throw new ConversionException(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object o) {
        try {
            return new JsonTypedOutPut(gson.toJson(o).getBytes(CHARSET_DEFAULT), CHARSET_DEFAULT);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutPut implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        public JsonTypedOutPut(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}
