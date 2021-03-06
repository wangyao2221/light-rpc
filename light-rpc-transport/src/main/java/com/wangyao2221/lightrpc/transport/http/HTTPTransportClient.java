package com.wangyao2221.lightrpc.transport.http;

import com.wangyao2221.lightrpc.proto.Peer;
import com.wangyao2221.lightrpc.proto.codec.Decoder;
import com.wangyao2221.lightrpc.proto.codec.Encoder;
import com.wangyao2221.lightrpc.transport.TransportClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wangyao2221
 * @date 2020/6/26 22:58
 */
public class HTTPTransportClient implements TransportClient {
    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public void init(Encoder encoder, Decoder decoder) {
    }

    @Override
    public Object write(Object data) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.connect();

            IOUtils.copy((InputStream) data, connection.getOutputStream());
            int resultCode = connection.getResponseCode();
            if (resultCode == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            } else {
                return connection.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
